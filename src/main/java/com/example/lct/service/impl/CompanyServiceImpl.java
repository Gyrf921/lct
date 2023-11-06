package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.mapper.CompanyMapper;
import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.service.CompanyService;
import com.example.lct.service.RoleService;
import com.example.lct.web.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;

    private final RoleService roleService;

    @Override
    public Company createCompany(CompanyDTO companyDTO) {
        log.info("[createCompany] >> companyDTO: {}", companyDTO);

        Company company = companyRepository.save(companyMapper.companyDTOToCompany(companyDTO));

        company.setRoles(roleService.createBaseRoleForCompany(company.getCompanyId()));

        Company savedCompany = companyRepository.save(company);

        log.info("[createCompany] << result: {}", savedCompany);

        return savedCompany;
    }

    @Override
    public Company saveCompany(Company company) {
        log.info("[saveCompany] >> company: {}", company);

        Company savedCompany = companyRepository.save(company);

        log.info("[createCompany] << result: {}", savedCompany);

        return savedCompany;
    }

    @Override
    public Company setAdmin(Company company, Employee employee) {

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        company.setEmployees(employees);

        Company savedCompany = companyRepository.save(company);

        log.info("[setAdmin] << result: {}", savedCompany);
        return savedCompany;
    }

    @Override
    public Company getCompanyById(Long companyIdByUserPrincipal) {
        log.info("[getCompanyById] >> companyIdByUserPrincipal: {}", companyIdByUserPrincipal);

        Company company = companyRepository.findById(companyIdByUserPrincipal)
                .orElseThrow(() -> {
                    log.error("Company not found by this id :{} ", companyIdByUserPrincipal);
                    return new ResourceNotFoundException("Company not found by this id :: " + companyIdByUserPrincipal);
                });

        log.info("[getCompanyById] << result: {}", company.getName());

        return company;
    }
}
