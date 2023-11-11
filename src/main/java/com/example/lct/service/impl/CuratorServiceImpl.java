package com.example.lct.service.impl;

import com.example.lct.model.*;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.repository.CompanyRepository;
import com.example.lct.service.*;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.StageWithoutTasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import com.example.lct.web.dto.response.StageResponseDTO;
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

    private final HistoryService historyService;
    private final TaskService taskService;
    private final StageService stageService;

    private final EmployeeService employeeService;
    private final EmailService emailService;

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
    public List<Task> createTasks(Company companyByUserPrincipal, List<TaskDTO> tasksDTO) {
        List<Task> tasks = taskService.createTasks(companyByUserPrincipal.getCompanyId(), tasksDTO);

        List<Task> tasksSaved;
        if (companyByUserPrincipal.getTasks() == null || companyByUserPrincipal.getTasks().isEmpty()) {
            tasksSaved = new ArrayList<>(tasks);
        } else {
            tasksSaved = companyByUserPrincipal.getTasks();
            tasksSaved.addAll(tasks);
        }

        companyByUserPrincipal.setTasks(tasksSaved);

        Company company = companyRepository.save(companyByUserPrincipal);

        log.info("[createTasksPlanForCompany] << result: {}", tasks);

        return company.getTasks();
    }

    @Override
    public List<Task> createTask(Company companyByUserPrincipal, TaskDTO taskDTO) {
        Task task = taskService.createTask(companyByUserPrincipal.getCompanyId(), taskDTO);

        List<Task> tasksSaved;

        if (companyByUserPrincipal.getTasks() == null || companyByUserPrincipal.getTasks().isEmpty()) {
            tasksSaved = new ArrayList<>(List.of(task));
        } else {
            tasksSaved = companyByUserPrincipal.getTasks();
            tasksSaved.add(task);
        }

        companyByUserPrincipal.setTasks(tasksSaved);

        Company company = companyRepository.save(companyByUserPrincipal);

        log.info("[createTask] << result: {}", task);

        return company.getTasks();
    }

    @Override
    public List<Task> updateTask(Company company, Long taskId, TaskDTO taskDTO) {
        log.info("[CuratorService|updateTask] >> companyId: {} taskId: {}, taskDTO: {}", company.getCompanyId(), taskId, taskDTO);
        Task task = taskService.updateTaskInfo(taskId, taskDTO);

        log.info("[CuratorService|updateTask] << result: {}", task);

        return company.getTasks();
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
    public List<StageResponseDTO> createStageToIntern(Long internId, StageDTO stageDTO) {
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

        List<StageResponseDTO> responseDTOS = new ArrayList<>();

        for (Stage stage: employee.getStages()) {
            List<Task> tasksInStage = stageService.getTaskFromStage(stage);

            responseDTOS.add(new StageResponseDTO(stage, tasksInStage));
        }

        return responseDTOS;
    }

    @Override
    public List<StageResponseDTO> createStageToInternWithoutTask(Long internId, StageWithoutTasksDTO stageWithoutTasksDTO) {
        log.info("[CuratorService|createStageToIntern] >> internId: {}, stageDTO: {}", internId, stageWithoutTasksDTO);

        Employee intern = employeeService.getEmployeeById(internId);

        List<Stage> internStages;

        if (intern.getStages() == null || intern.getStages().isEmpty()){
            internStages = new ArrayList<>(List.of(stageService.createStageForInternWithoutTask(intern, stageWithoutTasksDTO)));
        }
        else {
            internStages = intern.getStages();
            internStages.add(stageService.createStageForInternWithoutTask(intern, stageWithoutTasksDTO));
        }

        intern.setStages(internStages);

        Employee employee = employeeService.saveEmployee(intern);

        log.info("[CuratorService|createStageToIntern] << result: {}", employee.getStages());

        List<StageResponseDTO> responseDTOS = new ArrayList<>();

        for (Stage stage: employee.getStages()) {
            List<Task> tasksInStage = stageService.getTaskFromStage(stage);

            responseDTOS.add(new StageResponseDTO(stage, tasksInStage));
        }

        return responseDTOS;
    }

    @Override
    public Stage setTestToStage(Long stageId, TestDTO testDTO) {
        return stageService.setTestToStage(stageId, testDTO.getTestUrl());
    }
    @Override
    public List<StageResponseDTO> setTaskToStage(Long stageId, Long taskId) {

        Stage stageSaved = stageService.setTaskToStage(stageId, taskId);

        Employee employee = employeeService.getEmployeeById(stageSaved.getEmployeeId());
        log.info("[CuratorService|setTaskToStage] << result: {}", employee.getStages());

        List<StageResponseDTO> responseDTOS = new ArrayList<>();

        for (Stage stage: employee.getStages()) {
            List<Task> tasksInStage = stageService.getTaskFromStage(stage);

            responseDTOS.add(new StageResponseDTO(stage, tasksInStage));
        }

        return responseDTOS;
    }
    @Override
    public void evaluateInternAnswer(Long internId, Long taskId, Boolean isAccepted) {
        Employee intern = employeeService.getEmployeeById(internId);
        TaskStage taskStage = stageService.getTaskStageById(taskId);

        if (Boolean.TRUE.equals(isAccepted)){
            taskStage.setStatus(Status.ACCEPTED);
            taskStage.setTimeFinish(Timestamp.valueOf(LocalDateTime.now()));

            historyService.createHistoryActionCompleted(intern, HistoryType.TASK, String.format("Задача с id: %s, принята", taskId));
        }
        else{
            taskStage.setStatus(Status.REWRITE);
        }
        
        emailService.sendMarkToTaskByCurator(intern, taskStage);
    }



}
