package com.example.lct.model.factory;

import com.example.lct.mapper.EmployeeMapper;
import com.example.lct.model.Employee;
import com.example.lct.model.Post;
import com.example.lct.model.Task;
import com.example.lct.model.status.Department;
import com.example.lct.model.status.Role;
import com.example.lct.repository.EmployeeRepository;
import com.example.lct.service.RoleService;
import com.example.lct.service.impl.DepartmentServiceImpl;
import com.example.lct.service.impl.PostServiceImpl;
import com.example.lct.service.impl.RoleServiceImpl;
import com.example.lct.service.impl.TaskServiceImpl;
import com.example.lct.web.dto.request.admin.obj.EmployeeDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeFactory {
    private EmployeeFactory(){}
    private static RoleService roleService;
    private static DepartmentServiceImpl departmentService;
    private static PostServiceImpl postService;
    private static TaskServiceImpl taskService;



    @Autowired
    public void setRoleService(RoleServiceImpl roleService) {
        EmployeeFactory.roleService = roleService;
    }
    @Autowired
    public void setDepartmentService(DepartmentServiceImpl departmentService) {EmployeeFactory.departmentService = departmentService;}
    @Autowired
    public void setTaskService(TaskServiceImpl taskService) {
        EmployeeFactory.taskService = taskService;
    }

    private static final int START_DIFFICULT_LEVEL = 1;

    public static Employee createIntern(Long companyId, Long hrId, EmployeeDTO employeeDTO) {
        Post post = getPostForEmployee(companyId, employeeDTO.getPostName());

        List<Role> roles = new ArrayList<>();
        roles.add(getRoleForEmployee(companyId, employeeDTO.getRoleName()));

        List<Task> tasks
                = new ArrayList<>(getTasksForEmployee(companyId, post, START_DIFFICULT_LEVEL));

        return Employee.builder()
                .companyId(companyId)
                .email(employeeDTO.getEmail())
                .post(post)
                .roles(roles)
                .levelDifficulty(START_DIFFICULT_LEVEL)
                .tasks(tasks)
                .curatorId(hrId)
                .account(100L).build();
    }

    public static Employee createEmployee(Long companyId, EmployeeDTO employeeDTO) {
        Post post = getPostForEmployee(companyId, employeeDTO.getPostName());

        List<Role> roles = new ArrayList<>();
        roles.add(getRoleForEmployee(companyId, employeeDTO.getRoleName()));

        List<Task> tasks
                = new ArrayList<>(getTasksForEmployee(companyId, post, START_DIFFICULT_LEVEL));

        return Employee.builder()
                .companyId(companyId)
                .email(employeeDTO.getEmail())
                .post(post)
                .roles(roles)
                .tasks(tasks).build();
    }

    private static Department getDepartmentForEmployee(Long companyId, String departmentName) {
        return departmentService.getDepartmentByNameAndCompanyId(departmentName, companyId);
    }
    private static Post getPostForEmployee(Long companyId, String departmentName) {
        return postService.getPostByNameAndCompanyId(companyId, departmentName);
    }
    private static Role getRoleForEmployee(Long companyId, String departmentName) {
        return roleService.getRoleByNameAndCompany(departmentName, companyId);
    }
    private static List<Task> getTasksForEmployee(Long companyId, Post post, Integer levelDifficult) {
        return taskService.getTasks(companyId, post, levelDifficult);
    }

}
