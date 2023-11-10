package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.web.dto.request.EmployeePersonalityDTO;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import com.example.lct.web.dto.request.admin.EmployeeListForCreateDTO;
import com.example.lct.web.dto.request.admin.FilterTeamDTO;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.response.EmployeePersonalityResponseDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import com.example.lct.web.dto.response.obj.JwtResponseDTO;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeByEmail(String email);

    Employee getEmployeeById(Long id);

    JwtResponseDTO createTokenForUser(String email);

    List<Employee> getAllIntern();

    List<Employee> createEmployeesByAdmin(Long companyId, EmployeeListForCreateDTO employeesByAdmin);

    Employee registrationEmployee(RegistrationUserDTO registrationUserDTO);

    Employee createAdmin(Company company, RegistrationUserDTO registrationAdminDTO);

    Employee createIntern(Long companyId, EmployeeForCreateDTO employeeForCreateDTO);

    List<EmployeeTeamResponseDTO> getAllInternByCuratorId(Long curatorId);

    Employee saveEmployee(Employee employee);

    EmployeePersonalityResponseDTO getEmployeeInformation(Employee employee);

    EmployeePersonalityResponseDTO setEmployeeInformation(Employee employee, EmployeePersonalityDTO employeePersonalityDTO);

    List<EmployeeTeamResponseDTO> getTeamWithFilter(Company company, FilterTeamDTO filterTeamDTO);
    List<EmployeeTeamResponseDTO> getTeam(Company company);

    List<Employee> getInternsByCuratorId(Long curatorId);

    Employee createEmployeeByHR(Company company, EmployeeForCreateDTO employeeForCreateDTO);

    void deleteEmployee(Employee employee);
}
