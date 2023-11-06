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

    public Long getCompanyIdByUserPrincipal(Principal principal){

        Employee employee = employeeService.getEmployeeByEmail(principal.getName());
        //TODO check on null company Id => Exception Unauthorized
        return employee.getCompanyId();
    }
    public Long getEmployeeIdByUserPrincipal(Principal principal){

        Employee employee = employeeService.getEmployeeByEmail(principal.getName());

        return employee.getEmployeeId();
    }

    public Company getCompanyByUserPrincipal(Principal principal){

        Company company = companyService.getCompanyById(getCompanyIdByUserPrincipal(principal));

        log.info("[getCompanyByUserPrincipal] << result: {}", company);

        return company;
    }
}
