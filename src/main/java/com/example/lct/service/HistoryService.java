package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.History;
import com.example.lct.model.enumformodel.ActionType;
import com.example.lct.model.enumformodel.HistoryType;

public interface HistoryService {


    History createHistoryActionCreate(Employee employee, HistoryType type);

    History createHistoryActionRead(Employee employee, HistoryType type);

    History createHistoryActionDelete(Employee employee, HistoryType type);

    History createHistoryActionUpdate(Employee employee, HistoryType type);

    History createHistoryActionMiss(Employee employee, HistoryType type);

    History createHistoryActionOther(Employee employee, HistoryType type);

    History createHistoryActionOther(Employee employee, HistoryType type, String name);
}
