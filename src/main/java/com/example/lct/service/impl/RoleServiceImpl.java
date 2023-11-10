package com.example.lct.service.impl;

import com.example.lct.model.Role;
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
    public List<Role> getRolesByName(String roleName) {
        log.info("[getRolesByName] >> roleName: {}", roleName);

        List<Role> roles = roleRepository.findAllByName(roleName);

        log.info("[getRolesByName] << result: {}", roles);
        return roles;
    }

    @Override
    public Role getRoleByNameAndCompany(Long companyId, String roleName) {
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
                Role.builder().name("ROLE_CURATOR").companyId(companyId).build(),
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
