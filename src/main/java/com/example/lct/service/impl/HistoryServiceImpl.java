package com.example.lct.service.impl;

import com.example.lct.model.Employee;
import com.example.lct.model.History;
import com.example.lct.model.enumformodel.ActionType;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.repository.HistoryRepository;
import com.example.lct.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    @Override
    public List<History> getAllHistoryForEmployee(Employee employee) {
        return historyRepository.findAllByEmployee(employee);
    }

    @Override
    public History createHistoryActionCreate(Employee employee, HistoryType type, String name) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.CREATE)
                .name(name).build());
    }

    @Override
    public History createHistoryActionRead(Employee employee, HistoryType type, String name) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.READ)
                .name(name).build());
    }

    @Override
    public History createHistoryActionDelete(Employee employee, HistoryType type, String name) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.DELETE)
                .name(name).build());
    }

    @Override
    public History createHistoryActionUpdate(Employee employee, HistoryType type, String name) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.UPDATE)
                .name(name).build());
    }

    @Override
    public History createHistoryActionMiss(Employee employee, HistoryType type, String name) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.MISS)
                .name(name).build());
    }

    @Override
    public History createHistoryActionCompleted(Employee employee, HistoryType type, String name) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.COMPLETED)
                .name(name).build());
    }

    @Override
    public History createHistoryActionOther(Employee employee, HistoryType type, String name) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.OTHER)
                .name(name).build());
    }


}
