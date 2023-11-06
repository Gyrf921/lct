package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.status.Role;
import com.example.lct.repository.RoleRepository;
import com.example.lct.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role getRoleByName(String roleName) {
        log.info("[getRoleByName] >> roleName: {}", roleName);

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> {
                    log.error("Role not found by this name :{} ", roleName);
                    return new ResourceNotFoundException("Role not found by this name :: " + roleName);
                });

        log.info("[getRoleByName] << result: {}", role);
        return role;
    }

    @Override
    public Role getRoleByNameAndCompany(String roleName, Long companyId) {
        log.info("[getRoleByName] >> roleName: {}", roleName);

        List<Role> role = roleRepository.findAllByName(roleName).stream()
                .filter(role1 -> role1.getCompanyId().equals(companyId)).toList();

        log.info("[getRoleByName] << result: {}", role.get(0));
        return role.get(0);
    }

    @Override
    public List<Role> getAllRole() {
        log.info("[getAllRole] >> without params");

        List<Role> role = roleRepository.findAll();

        log.info("[getAllRole] << result: {}", role);

        return role;
    }

    @Override
    public List<Role> createBaseRoleForCompany(Long companyId) {
        log.info("[createBaseRoleForCompany] admin, hr, employee, intern");

        List<Role> listRoles = List.of(
                Role.builder().name("ROLE_ADMIN").companyId(companyId).build(),
                Role.builder().name("ROLE_HR").companyId(companyId).build(),
                Role.builder().name("ROLE_EMPLOYEE").companyId(companyId).build(),
                Role.builder().name("ROLE_INTERN").companyId(companyId).build()
        );

        List<Role> savedRoles = roleRepository.saveAll(listRoles);

        log.info("[createBaseRoleForCompany] << result: {}", savedRoles);

        return savedRoles;
    }

    @Override
    public Role createRole(String roleName) {
        log.info("[createRole] >> roleName: {}", roleName);

        Role savedRole = roleRepository.save(
                Role.builder()
                        .name(roleName)
                        .build()
        );

        log.info("[createRole] << result: {}", savedRole);

        return savedRole;
    }
}
