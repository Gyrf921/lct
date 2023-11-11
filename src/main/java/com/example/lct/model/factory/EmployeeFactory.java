package com.example.lct.model.factory;

import com.example.lct.mapper.EmployeeMapper;
import com.example.lct.model.Employee;
import com.example.lct.model.Post;
import com.example.lct.model.Role;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.RoleService;
import com.example.lct.service.impl.PostServiceImpl;
import com.example.lct.service.impl.RoleServiceImpl;
import com.example.lct.web.dto.request.EmployeePersonalityDTO;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeFactory {
    private EmployeeFactory() {
    }

    @Autowired
    public void setService(RoleServiceImpl roleService, EmployeeService employeeService, PostServiceImpl postService, EmployeeMapper employeeMapper) {
        EmployeeFactory.roleService = roleService;
        EmployeeFactory.employeeService = employeeService;
        EmployeeFactory.postService = postService;
        EmployeeFactory.employeeMapper = employeeMapper;
    }

    private static RoleService roleService;
    private static EmployeeService employeeService;
    private static PostServiceImpl postService;

    private static EmployeeMapper employeeMapper;

    private static final int START_DIFFICULT_LEVEL = 1;
    private static final String ROLE_INTERN_NAME = "ROLE_INTERN";


    /**
     * Create employee or intern
     * @param companyId - companyId where with employee working
     * @param employeeForCreateDTO - DTO with employee data
     * @return return employee or intern, intern has levelDifficulty = 1, account = 100, base stage and curator, for employee this field is null
     */
    public static Employee createEmployee(Long companyId, EmployeeForCreateDTO employeeForCreateDTO) {
        Post post = postService.getPostByNameAndCompanyId(companyId, employeeForCreateDTO.getPostName());

        List<Role> roles = new ArrayList<>(List.of(roleService.getRoleByNameAndCompany(companyId, employeeForCreateDTO.getRoleName())));

        Employee employee = Employee.builder()
                .companyId(companyId)
                .name(employeeForCreateDTO.getName())
                .email(employeeForCreateDTO.getEmail())
                .post(post)
                .roles(roles)
                .account(0L).build();

        if (isIntern(employeeForCreateDTO)) {
            return createIntern(employeeForCreateDTO, employee);
        }
        return employee;
    }

    public static Employee updateEmployeeInfo(Employee employeeByUserPrincipal, EmployeePersonalityDTO employeePersonalityDTO) {
        return employeeByUserPrincipal.toBuilder()
                .imagePath(employeePersonalityDTO.getImagePath())
                .name(employeePersonalityDTO.getName())
                .phone(employeePersonalityDTO.getPhone())
                .socialNetwork(employeePersonalityDTO.getSocialNetwork())
                .city(employeePersonalityDTO.getCity()).build();
    }

    private static Employee createIntern(EmployeeForCreateDTO employeeForCreateDTO, Employee employee) {
        Long curatorId = employeeService.getEmployeeByEmail(employeeForCreateDTO.getCuratorEmail()).getCuratorId();

        return employee.toBuilder()
                .levelDifficulty(START_DIFFICULT_LEVEL)
                .curatorId(curatorId)
                .account(100L).build();
    }

    private static boolean isIntern(EmployeeForCreateDTO employeeForCreateDTO) {
        return employeeForCreateDTO.getRoleName().equals(ROLE_INTERN_NAME);
    }


}
