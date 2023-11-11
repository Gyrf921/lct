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
            if (Boolean.TRUE.equals(isHistoryForAnalytic(history))) {
                analyticList.add(new AnalyticDTO(history.getHistoryType(), 1));
            }
        }
        return sumCounts(analyticList);
    }

    @Override
    public List<AnalyticDTO> getAnalyticByEmployee(Employee employee) {
        List<History> totalHistories = historyService.getAllHistoryForEmployee(employee);
        List<AnalyticDTO> analyticList = new ArrayList<>();

        for(History history : totalHistories){
            if (Boolean.TRUE.equals(isHistoryForAnalytic(history))) {
                analyticList.add(new AnalyticDTO(history.getHistoryType(), 1));
            }
        }
        return sumCounts(analyticList);
    }

    public List<AnalyticDTO> sumCounts(List<AnalyticDTO> inputList) {
        Map<HistoryType, Integer> countMap = new HashMap<>();

        for (AnalyticDTO dto : inputList) {
            HistoryType name = dto.getName();
            countMap.put(name, countMap.getOrDefault(name, 0) + 1);
        }

        List<AnalyticDTO> resultList = new ArrayList<>();
        for (Map.Entry<HistoryType, Integer> entry : countMap.entrySet()) {
            AnalyticDTO resultDTO = new AnalyticDTO();
            resultDTO.setName(entry.getKey());
            resultDTO.setCountDone(entry.getValue());
            resultList.add(resultDTO);
        }

        return resultList;
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
}
