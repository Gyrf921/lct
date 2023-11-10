package com.example.lct.util;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.service.CompanyService;
import com.example.lct.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserPrincipalUtils {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    public Company getCompanyByUserPrincipal(Principal principal) {
        log.info("[UserPrincipalUtils|getCompany_ByUP] >> user principal: {}", principal.getName());

        Employee employee = employeeService.getEmployeeByEmail(principal.getName());

        Company company = companyService.getCompanyById(employee.getCompanyId());

        log.info("[UserPrincipalUtils|getCompany_ByUP] << result is company with Id: {}", company.getCompanyId());
        return company;
    }

    public Employee getEmployeeByUserPrincipal(Principal principal) {
        log.info("[UserPrincipalUtils|getEmployee_ByUP] >> user principal: {}", principal.getName());

        Employee employee = employeeService.getEmployeeByEmail(principal.getName());

        log.info("[UserPrincipalUtils|getEmployee_ByUP] << result is employee with Id: {}", employee.getEmployeeId());
        return employee;
    }


}
