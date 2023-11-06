package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.web.dto.CompanyDTO;

public interface CompanyService {
    Company createCompany(CompanyDTO companyDTO);

    Company saveCompany(Company company);

    Company setAdmin(Company company, Employee employee);

    Company getCompanyById(Long companyIdByUserPrincipal);
}
