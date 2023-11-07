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

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    @Override
    public History createHistoryActionCreate(Employee employee, HistoryType type) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.CREATE)
                .name(type.name() + " " + ActionType.CREATE.name()).build());
    }
    @Override
    public History createHistoryActionRead(Employee employee, HistoryType type) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.READ)
                .name(type.name() + " " + ActionType.READ.name()).build());
    }
    @Override
    public History createHistoryActionDelete(Employee employee, HistoryType type) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.DELETE)
                .name(type.name() + " " + ActionType.DELETE.name()).build());
    }
    @Override
    public History createHistoryActionUpdate(Employee employee, HistoryType type) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.UPDATE)
                .name(type.name() + " " + ActionType.UPDATE.name()).build());
    }
    @Override
    public History createHistoryActionMiss(Employee employee, HistoryType type) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.MISS)
                .name(type.name() + " " + ActionType.MISS.name()).build());
    }
    @Override
    public History createHistoryActionOther(Employee employee, HistoryType type) {
        return historyRepository.save(History.builder()
                .companyId(employee.getCompanyId())
                .employee(employee)
                .historyType(type)
                .actionType(ActionType.OTHER)
                .name(type.name() + " " + ActionType.OTHER.name()).build());
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
