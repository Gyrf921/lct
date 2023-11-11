package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.History;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.web.dto.response.AnalyticDTO;

import java.util.List;

public interface HistoryService {

    List<History> getAllHistoryForEmployee(Employee employee);

    History createHistoryActionCreate(Employee employee, HistoryType type, String name);

    History createHistoryActionRead(Employee employee, HistoryType type, String name);

    History createHistoryActionDelete(Employee employee, HistoryType type, String name);

    History createHistoryActionUpdate(Employee employee, HistoryType type, String name);

    History createHistoryActionMiss(Employee employee, HistoryType type, String name);

    History createHistoryActionCompleted(Employee employee, HistoryType type, String name);

    History createHistoryActionOther(Employee employee, HistoryType type, String name);
}
