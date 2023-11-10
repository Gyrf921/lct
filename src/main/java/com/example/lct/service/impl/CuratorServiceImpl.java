package com.example.lct.service.impl;

import com.example.lct.model.*;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.service.*;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CuratorServiceImpl implements CuratorService {

    private final DepartmentServiceImpl departmentService;

    private final TaskService taskService;
    private final StageService stageService;

    private final EmployeeService employeeService;

    private final CompanyRepository companyRepository;

    @Override
    public List<TaskStage> getTaskStagesForCuratorChecking(Long curatorId) {
        List<Employee> interns = employeeService.getInternsByCuratorId(curatorId);
        return getCompletedTasksForCurator(interns);
    }

    private List<TaskStage> getCompletedTasksForCurator(List<Employee> interns){
        List<TaskStage> tasksForCheck = new ArrayList<>();
        for (Employee intern: interns){
            tasksForCheck.addAll(stageService.getAllTaskStageForEmployee(intern));
        }
        return tasksForCheck.stream().filter(taskStage -> taskStage.getStatus().equals(Status.DONE)).toList();
    }
    @Override
    public List<Task> createTasksForCompany(Company companyByUserPrincipal, TasksDTO tasksDTO) {
        List<Task> tasks = taskService.createTasks(companyByUserPrincipal.getCompanyId(), tasksDTO);

        List<Task> tasksSaved;
        if (companyByUserPrincipal.getTasks() == null || companyByUserPrincipal.getTasks().isEmpty()) {
            tasksSaved = new ArrayList<>(tasks);
        } else {
            tasksSaved = companyByUserPrincipal.getTasks();
            tasksSaved.addAll(tasks);
        }

        companyByUserPrincipal.setTasks(tasksSaved);

        companyRepository.save(companyByUserPrincipal);

        log.info("[createTasksPlanForCompany] << result: {}", tasks);

        return tasks;
    }

    @Override
    public List<Task> createBaseTasksForCompany(Company companyByUserPrincipal, TasksDTO tasksDTO) {

        List<Task> tasks = taskService.createBaseTasks(companyByUserPrincipal.getCompanyId(), tasksDTO);

        List<Task> tasksSaved;
        if (companyByUserPrincipal.getTasks() == null || companyByUserPrincipal.getTasks().isEmpty()) {
            tasksSaved = new ArrayList<>(tasks);
        } else {
            tasksSaved = companyByUserPrincipal.getTasks();
            tasksSaved.addAll(tasks);
        }

        companyByUserPrincipal.setTasks(tasksSaved);

        companyRepository.save(companyByUserPrincipal);

        log.info("[createTasksPlanForCompany] << result: {}", tasks);

        return tasks;
    }

    @Override
    public Employee createIntern(Company companyByUserPrincipal, EmployeeForCreateDTO employeeForCreateDTO) {
        //создать пользователя, добавить к нему задания и hr
        Employee intern = employeeService.createIntern(companyByUserPrincipal.getCompanyId(), employeeForCreateDTO);

        companyByUserPrincipal.getEmployees().add(intern);

        Company savedCompany = companyRepository.save(companyByUserPrincipal);

        log.info("[createInternForCompany] << result company: {}", savedCompany);

        return intern;
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
    public Employee createStageToIntern(Long internId, StageDTO stageDTO) {
        Employee intern = employeeService.getEmployeeById(internId);
        intern.setStages(List.of(stageService.createStageForIntern(intern, stageDTO)));
        return employeeService.saveEmployee(intern);
    }

    @Override
    public Stage setTestToStage(Long stageId, TestDTO testDTO) {
        return stageService.setTestToStage(stageId, testDTO.getTestUrl());
    }


}
