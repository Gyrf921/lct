package com.example.lct.service.impl;

import com.example.lct.model.Company;
import com.example.lct.model.Department;
import com.example.lct.model.Employee;
import com.example.lct.model.History;
import com.example.lct.model.enumformodel.ActionType;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class AnalyticalServiceImpl {
    private final EmployeeService employeeService;
    private final HistoryService historyService;
    private final DepartmentServiceImpl departmentService;

    public Map<HistoryType, Integer> getAnalyticByDepartment(Company company, String departmentName){
        Department department = departmentService.getDepartmentByNameAndCompanyId(company.getCompanyId(), departmentName);

        List<Employee> employeesInDep = company.getEmployees().stream()
                .filter(employee -> employee.getPost().getDepartment().equals(department)).toList();

        List<History> totalHistories = employeesInDep.stream()
                .map(historyService::getAllHistoryForEmployee)
                .flatMap(Collection::stream)
                .toList();

        Map<HistoryType, Integer> analiticMap = new HashMap<>();
        for(History history : totalHistories){
            setRecordAnalytic(analiticMap, history);
        }

        return analiticMap;
    }
    private Map<HistoryType, Integer> setRecordAnalytic(Map<HistoryType, Integer> analiticMap, History history){
        if (Boolean.TRUE.equals(isHistoryForAnalytic(history))){
            plusRecordAnalytic(analiticMap, history.getHistoryType());
        }
        return analiticMap;
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
    private Map<HistoryType, Integer> plusRecordAnalytic(Map<HistoryType, Integer> analiticMap, HistoryType type){
        if (!analiticMap.containsKey(type)){
            analiticMap.put(type, 0);
        }
        analiticMap.put(type, analiticMap.get(type) + 1);
        return analiticMap;
    }
}
