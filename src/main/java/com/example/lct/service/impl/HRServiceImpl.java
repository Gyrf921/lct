package com.example.lct.service.impl;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.EmployeeLinkTask;
import com.example.lct.model.Task;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HRService;
import com.example.lct.service.TaskService;
import com.example.lct.web.dto.request.admin.obj.EmployeeDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HRServiceImpl implements HRService {

    private final DepartmentServiceImpl departmentService;

    private final TaskService taskService;

    private final EmployeeService employeeService;

    private final CompanyRepository companyRepository;

    @Override
    public List<Task> createTasksPlanForCompany(Company companyByUserPrincipal, TasksDTO tasksDTO) {

        List<Task> tasks = taskService.createTasks(companyByUserPrincipal.getCompanyId(), tasksDTO);

        companyByUserPrincipal.setTasks(tasks);

        companyRepository.save(companyByUserPrincipal);

        log.info("[createTasksPlanForCompany] << result: {}", tasks);

        return tasks;
    }

    @Override
    public Task updateTaskInfoForCompany(Long taskId, TaskDTO taskDTO) {
        return taskService.updateTaskInfo(taskId, taskDTO);
    }

    @Override
    public Employee createInternForCompany(Company companyByUserPrincipal, Long hrId, EmployeeDTO employeeDTO) {
        //создать пользователя, добавить к нему задания и hr
        Employee intern = employeeService.createIntern(companyByUserPrincipal.getCompanyId(), hrId, employeeDTO);

        companyByUserPrincipal.getEmployees().add(intern);

        Company savedCompany = companyRepository.save(companyByUserPrincipal);

        log.info("[createInternForCompany] << result company: {}", savedCompany);

        return intern;
    }

    @Override
    public List<EmployeeLinkTask> getAllTaskForCuratorChecking(Long curatorId) {

        return taskService.getAllTaskForCuratorChecking(curatorId);
    }

    @Override
    public List<Employee> getAllInternForHR(Long employeeIdByUserPrincipal) {
        return employeeService.getAllInternByCuratorId(employeeIdByUserPrincipal);
    }

    @Override
    public List<Task> getAllTasksForCompany(Company companyByUserPrincipal) {
        return companyByUserPrincipal.getTasks();
    }

    @Override
    public List<Task> getAllTasksForCompanyByLevel(Integer level, Company companyByUserPrincipal) {
        return companyByUserPrincipal.getTasks().stream()
                .filter(task -> task.getLevelDifficulty().equals(level))
                .toList();
    }
}
