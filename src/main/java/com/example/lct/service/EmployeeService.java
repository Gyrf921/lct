package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import com.example.lct.web.dto.request.admin.EmployeesDTO;
import com.example.lct.web.dto.request.admin.obj.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeByEmail(String email);

    String createTokenForUser(String email);

    List<Employee> createEmployeesByAdmin(Long companyId, EmployeesDTO employeesByAdmin);

    Employee registrationEmployee(RegistrationUserDTO registrationUserDTO);

    Employee createAdmin(Company company, RegistrationUserDTO registrationAdminDTO);

    Employee createIntern(Long companyId, Long hrId, EmployeeDTO employeeDTO);

    List<Employee> getAllInternByCuratorId(Long employeeIdByUserPrincipal);
}
