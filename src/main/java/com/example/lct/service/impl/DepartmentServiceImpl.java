package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Department;
import com.example.lct.repository.DepartmentRepository;
import com.example.lct.web.dto.request.admin.DepartmentsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentServiceImpl {

    private final DepartmentRepository departmentRepository;

    public List<Department> saveAllDepartmentForCompany(Long companyId, DepartmentsDTO departmentsDTO){
        List<Department> departments = new ArrayList<>();

        for (String departmentName: departmentsDTO.getDepartmentsName()) {
            departments.add(Department.builder().companyId(companyId).name(departmentName).build());
        }
        return departmentRepository.saveAll(departments);
    }

    public Department getDepartmentByNameAndCompanyId(Long companyId, String name) {

        log.info("[getDepartmentByNameAndCompanyId] >> name: {}, companyId: {}", name, companyId);

        Department savedDepartment = departmentRepository.findByNameAndCompanyId(name, companyId)
                .orElseThrow(() -> {
                    log.error("Department not found by this name on your company :: :{} ", name);
                    return new ResourceNotFoundException("Department not found by this name on your company :: " + name);
                });

        log.info("[getDepartmentByNameAndCompanyId] << result: {}", savedDepartment);

        return savedDepartment;
    }
}
