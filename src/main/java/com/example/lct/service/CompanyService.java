package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.web.dto.response.obj.CompanyDTO;

public interface CompanyService {
    Company createCompany(CompanyDTO companyDTO);

    Company saveCompany(Company company);

    Company saveAdmin(Company company, Employee employee);

    Company getCompanyById(Long companyIdByUserPrincipal);
}
