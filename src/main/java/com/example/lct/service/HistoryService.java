package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.History;
import com.example.lct.model.enumformodel.HistoryType;

public interface HistoryService {


    History createHistoryActionCreate(Employee employee, HistoryType type, String name);

    History createHistoryActionRead(Employee employee, HistoryType type, String name);

    History createHistoryActionDelete(Employee employee, HistoryType type, String name);

    History createHistoryActionUpdate(Employee employee, HistoryType type, String name);

    History createHistoryActionMiss(Employee employee, HistoryType type, String name);

    History createHistoryActionOther(Employee employee, HistoryType type, String name);
}
