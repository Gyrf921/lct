package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.enumformodel.HistoryType;

import java.util.Map;

public interface AnalyticalService {
    Map<HistoryType, Integer> getAnalyticByDepartment(Company company, String departmentName);
}
