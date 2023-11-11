package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.web.dto.response.AnalyticDTO;

import java.util.List;

public interface AnalyticalService {
    List<AnalyticDTO> getAnalyticByDepartment(Company company, String departmentName);
    List<AnalyticDTO> getAnalyticByEmployee(Employee employee);
}
