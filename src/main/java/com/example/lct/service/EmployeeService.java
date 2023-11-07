package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.web.dto.request.EmployeePersonalityDTO;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import com.example.lct.web.dto.request.admin.EmployeeListForCreateDTO;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.response.EmployeePersonalityResponseDTO;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeByEmail(String email);

    String createTokenForUser(String email);

    List<Employee> createEmployeesByAdmin(Long companyId, EmployeeListForCreateDTO employeesByAdmin);

    Employee registrationEmployee(RegistrationUserDTO registrationUserDTO);

    Employee createAdmin(Company company, RegistrationUserDTO registrationAdminDTO);

    Employee createIntern(Long companyId, EmployeeForCreateDTO employeeForCreateDTO);

    List<Employee> getAllInternByCuratorId(Long employeeIdByUserPrincipal);

    Employee saveEmployee(Employee employee);

    EmployeePersonalityResponseDTO getEmployeeInformation(Employee employeeByUserPrincipal);

    EmployeePersonalityResponseDTO setEmployeeInformation(Employee employeeByUserPrincipal, EmployeePersonalityDTO employeePersonalityDTO);
}