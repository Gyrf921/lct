package com.example.lct.service.impl;

import com.example.lct.model.Company;
import com.example.lct.model.Department;
import com.example.lct.model.Employee;
import com.example.lct.model.History;
import com.example.lct.model.enumformodel.ActionType;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.service.AnalyticalService;
import com.example.lct.service.HistoryService;
import com.example.lct.web.dto.response.AnalyticDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class AnalyticalServiceImpl implements AnalyticalService {
    private final HistoryService historyService;
    private final DepartmentServiceImpl departmentService;

    @Override
    public List<AnalyticDTO> getAnalyticByDepartment(Company company, String departmentName){
        Department department = departmentService.getDepartmentByNameAndCompanyId(company.getCompanyId(), departmentName);

        List<Employee> employeesInDep = company.getEmployees().stream()
                .filter(employee -> employee.getPost().getDepartment().equals(department)).toList();

        List<History> totalHistories = employeesInDep.stream()
                .map(historyService::getAllHistoryForEmployee)
                .flatMap(Collection::stream)
                .toList();

        List<AnalyticDTO> analyticList = new ArrayList<>();
        for(History history : totalHistories){
            setRecordAnalytic(analyticList, history);
        }

        return analyticList;
    }
    private List<AnalyticDTO> setRecordAnalytic(List<AnalyticDTO> analyticList, History history){
        if (Boolean.TRUE.equals(isHistoryForAnalytic(history))){
            plusRecordAnalytic(analyticList, history.getHistoryType());
        }
        return analyticList;
    }

    private Boolean isHistoryForAnalytic(History history) {
        return isHistoryTypeForRead(history) || isCompleted(history) || isDeadline(history);
    }

    private Boolean isHistoryTypeForRead(History history) {
        return history.getActionType().equals(ActionType.READ) && (history.getHistoryType().equals(HistoryType.ARTICLE) ||
                 history.getHistoryType().equals(HistoryType.VIDEO) || history.getHistoryType().equals(HistoryType.AUDIO));
    }
    private Boolean isCompleted(History history) {
        return history.getActionType().equals(ActionType.COMPLETED) && (history.getHistoryType().equals(HistoryType.TASK) ||
                history.getHistoryType().equals(HistoryType.TEST) || history.getHistoryType().equals(HistoryType.STAGE));
    }
    private Boolean isDeadline(History history) {
        return history.getActionType().equals(ActionType.MISS) && history.getHistoryType().equals(HistoryType.DEADLINE);
    }
    private List<AnalyticDTO> plusRecordAnalytic(List<AnalyticDTO> analyticList, HistoryType type){
        for (AnalyticDTO analytic : analyticList){
            if (!analytic.getName().equals(type)){
                analyticList.add(new AnalyticDTO(type, 0));
            }
            analytic.setCountDone(analytic.getCountDone() + 1);
        }
        return analyticList;
    }
}
