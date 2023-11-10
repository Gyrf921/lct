package com.example.lct.service.impl;

import com.example.lct.model.*;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HRService;
import com.example.lct.service.StageService;
import com.example.lct.service.TaskService;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HRServiceImpl implements HRService {

    private final EmployeeService employeeService;

    private final CompanyRepository companyRepository;


    @Override
    public List<EmployeeTeamResponseDTO> createIntern(Company company, EmployeeForCreateDTO employeeForCreateDTO) {
        log.info("[HRServiceImpl|createIntern] >> company: {}, employeeForCreateDTO: {}", company, employeeForCreateDTO);
        //создать пользователя, добавить к нему задания и hr
        Employee intern = employeeService.createIntern(company.getCompanyId(), employeeForCreateDTO);

        company.getEmployees().add(intern);

        Company savedCompany = companyRepository.save(company);

        log.info("[HRServiceImpl|createIntern] << result intern: {}", intern);

        return employeeService.getTeam(savedCompany);
    }

    @Override
    public List<EmployeeTeamResponseDTO> createEmployee(Company company, EmployeeForCreateDTO employeeForCreateDTO) {
        log.info("[HRServiceImpl|createEmployee] >> company: {}, employeeForCreateDTO: {}", company, employeeForCreateDTO);

        Employee employee = employeeService.createEmployeeByHR(company, employeeForCreateDTO);

        company.getEmployees().add(employee);

        Company savedCompany = companyRepository.save(company);

        log.info("[HRServiceImpl|createEmployee] << result employee: {}", employee);

        return employeeService.getTeam(savedCompany);
    }

    @Override
    public List<EmployeeTeamResponseDTO> deleteEmployee(Company company, Long employeeId)
    {
        log.info("[HRServiceImpl|deleteEmployee] >> company: {}, employeeId: {}", company, employeeId);

        Employee employee = employeeService.getEmployeeById(employeeId);
        employeeService.deleteEmployee(employee);

        company.getEmployees().remove(employee);

        Company savedCompany = companyRepository.save(company);

        log.info("[HRServiceImpl|deleteEmployee] << result employee: {} , was deleted", employee);

        return employeeService.getTeam(savedCompany);
    }


}
