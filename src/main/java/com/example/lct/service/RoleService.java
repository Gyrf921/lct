package com.example.lct.service;

import com.example.lct.model.Role;

import java.util.List;

public interface RoleService {
    /**
     * Find a roles in the database by its name.
     *
     * @param roleName The name of the role to find.
     * @return The role object from the database with the specified name.
     */
    List<Role> getRolesByName(String roleName);

    Role getRoleByNameAndCompany(Long companyId, String roleName);

    /**
     * Retrieve a list of all roles stored in the database.
     *
     * @return A list of all roles available in the database.
     */
    List<Role> getAllRole();

    /**
     * Create a new role with the provided name and save it in the database.
     *
     * @return The role object that has been saved in the database.
     */
    List<Role> createBaseRoleForCompany(Long companyId);

    /**
     * Create a new role with the provided name and save it in the database.
     *
     * @param roleName The name for the new role.
     * @return The role object that has been saved in the database.
     */
    Role createRole(String roleName);
}
