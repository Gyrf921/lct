package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.mapper.CompanyMapper;
import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.service.CompanyService;
import com.example.lct.service.RoleService;
import com.example.lct.web.dto.response.obj.CompanyDTO;
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
    private final PostServiceImpl postService;
    private final DepartmentServiceImpl departmentService;

    @Override
    public Company createCompany(CompanyDTO companyDTO) {
        log.info("[CompanyService|createCompany] >> companyDTO: {}", companyDTO);

        Company company = companyRepository.save(companyMapper.companyDTOToCompany(companyDTO));
        log.info("[CompanyService|createCompany <- companyMapper] == company: {}", company);

        Company savedCompany = companyRepository.save(setBaseField(company));

        log.info("[CompanyService|createCompany] << result: {}", savedCompany);

        return savedCompany;
    }

    private Company setBaseField(Company company){
        log.info("[CompanyService|setBaseField] >> company: {}", company);

        company.setRoles(roleService.createBaseRoleForCompany(company.getCompanyId()));
        company.setDepartments(departmentService.createBaseDepartmentForCompany(company.getCompanyId()));
        company.setPosts(postService.createBasePostForCompany(company.getCompanyId()));

        log.info("[CompanyService|setBaseField] << set base Role, department, Post");
        return company;
    }

    @Override
    public Company saveCompany(Company company) {
        log.info("[CompanyService|saveCompany] >> company: {}", company);

        Company savedCompany = companyRepository.save(company);

        log.info("[CompanyService|createCompany] << result: {}", savedCompany);

        return savedCompany;
    }

    @Override
    public Company saveAdmin(Company company, Employee admin) {
        log.info("[CompanyService|saveCompany] >> company: {}, employeeId: {}", company, admin.getEmployeeId());
        List<Employee> employees;

        if (company.getEmployees() == null ||company.getEmployees().isEmpty()){
            employees = new ArrayList<>();
            employees.add(admin);
        }else{
            employees = company.getEmployees();
            employees.add(admin);
        }//todo роль и департамент
        company.setEmployees(employees);

        Company savedCompany = companyRepository.save(company);

        log.info("[CompanyService|setAdmin] << result: {}", savedCompany);
        return savedCompany;
    }

    @Override
    public Company getCompanyById(Long companyIdByUserPrincipal) {
        log.info("[CompanyService|getCompanyById] >> companyIdByUserPrincipal: {}", companyIdByUserPrincipal);

        Company company = companyRepository.findById(companyIdByUserPrincipal)
                .orElseThrow(() -> {
                    log.error("CompanyService|Company not found by this id :{} ", companyIdByUserPrincipal);
                    return new ResourceNotFoundException("CompanyService|Company not found by this id :: " + companyIdByUserPrincipal);
                });

        log.info("[CompanyService|getCompanyById] << result company: {}", company);

        return company;
    }
}
