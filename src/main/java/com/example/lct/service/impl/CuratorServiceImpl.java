package com.example.lct.service.impl;

import com.example.lct.model.*;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.service.*;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import com.example.lct.web.dto.response.TaskForCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public List<TaskForCheckDTO> getTaskStagesForCuratorChecking(Long curatorId) {
        List<Employee> interns = employeeService.getInternsByCuratorId(curatorId);
        return getCompletedTasksForCurator(interns);
    }

    private List<TaskForCheckDTO> getCompletedTasksForCurator(List<Employee> interns){
        List<TaskStage> tasksForCheck = new ArrayList<>();
        List<TaskForCheckDTO> taskForCheckDTOS = new ArrayList<>();

        for (Employee intern: interns){
            tasksForCheck.addAll(stageService.getAllTaskStageForEmployee(intern).stream()
                    .filter(taskStage -> taskStage.getStatus().equals(Status.DONE))
                    .toList());
            taskForCheckDTOS.addAll(mapListTaskStageToTaskForCheckDTO(intern, tasksForCheck));
        }

        return taskForCheckDTOS;
    }

    private List<TaskForCheckDTO> mapListTaskStageToTaskForCheckDTO(Employee employee, List<TaskStage> tasksStage){
        List<TaskForCheckDTO> taskForCheckDTOS = new ArrayList<>();

        for (TaskStage taskStage:tasksStage) {
            taskForCheckDTOS.add(TaskForCheckDTO.builder()
                    .employeeId(employee.getEmployeeId())
                    .employeeName(employee.getName())
                    .post(employee.getPost())
                    .taskStageId(taskStage.getTaskStageId())
                    .taskName(taskStage.getTask().getName()).build());
        }

        return taskForCheckDTOS;
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
    public List<EmployeeTeamResponseDTO> getCuratorInterns(Long employeeIdByUserPrincipal) {
        return employeeService.getAllInternByCuratorId(employeeIdByUserPrincipal);
    }

    @Override
    public List<Task> getAllTasksForCompany(Company companyByUserPrincipal) {
        return companyByUserPrincipal.getTasks();
    }

    @Override
    public List<Stage> createStageToIntern(Long internId, StageDTO stageDTO) {
        log.info("[CuratorService|createStageToIntern] >> internId: {}, stageDTO: {}", internId, stageDTO);

        Employee intern = employeeService.getEmployeeById(internId);

        List<Stage> internStages;

        if (intern.getStages() == null || intern.getStages().isEmpty()){
            internStages = new ArrayList<>(List.of(stageService.createStageForIntern(intern, stageDTO)));
        }
        else {
            internStages = intern.getStages();
            internStages.add(stageService.createStageForIntern(intern, stageDTO));
        }

        intern.setStages(internStages);

        Employee employee = employeeService.saveEmployee(intern);

        log.info("[CuratorService|createStageToIntern] << result: {}", employee.getStages());

        return employee.getStages();
    }

    @Override
    public Stage setTestToStage(Long stageId, TestDTO testDTO) {
        return stageService.setTestToStage(stageId, testDTO.getTestUrl());
    }


    @Override
    public void evaluateInternAnswer(Long taskId, Boolean isAccepted) {
        TaskStage taskStage = stageService.getTaskStageById(taskId);

        if (isAccepted){
            taskStage.setStatus(Status.ACCEPTED);
            taskStage.setTimeFinish(Timestamp.valueOf(LocalDateTime.now()));
        }
        else{
            taskStage.setStatus(Status.REWRITE);
        }
    }

}
