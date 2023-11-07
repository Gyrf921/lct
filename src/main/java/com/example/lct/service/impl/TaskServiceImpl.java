package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Employee;
import com.example.lct.model.TaskStage;
import com.example.lct.model.Post;
import com.example.lct.model.Task;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.model.factory.TaskFactory;
import com.example.lct.repository.TaskRepository;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.TaskService;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;
    @Override
    public Task getTaskById(Long id) {
        log.info("[getTaskById] >> id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Task not found by this id :{} ", id);
                    return new ResourceNotFoundException("Task not found by this id :: " + id);
                });

        log.info("[getTaskById] << result: {}", task);

        return task;
    }
    @Override
    public List<Task> createTasks(Long companyId, TasksDTO tasksDTO){

        List<Task> tasks = new ArrayList<>();

        for (TaskDTO taskDTO: tasksDTO.getTaskDTOList()) {
            tasks.add(TaskFactory.create(companyId, taskDTO));
        }

        tasks = taskRepository.saveAll(tasks);

        log.info("[createTasks] << result: {}", tasks);

        return tasks;
    }
    @Override
    public Task updateTaskInfo(Long taskId, TaskDTO taskDTO) {

        Task task = getTaskById(taskId);

        Task savedTask = taskRepository.save(TaskFactory.update(task, taskDTO));

        log.info("[updateTaskInfo] << result: {}", savedTask);

        return savedTask;
    }
    @Override
    public List<Task> getTasks(Long companyId, Post post, Integer levelDifficult) {

        List<Task> tasks = taskRepository
                .findAllByCompanyIdAndPostAndLevelDifficulty(companyId, post, levelDifficult);

        log.info("[getTasks] << result: {}", tasks);

        return tasks;
    }

    @Override
    public TaskStage getEmployeeLinkTaskById(Long id) {
        return null;
    }

    @Override
    public TaskStage setStatusTaskForEmployee(Long taskEmployeeId, Status status) {
        return null;
    }

    @Override
    public List<TaskStage> getEmployeeLinkTaskForEmployee(Employee employee, Status status) {
        return null;
    }

    @Override
    public List<TaskStage> getAllTaskForCuratorChecking(Long curatorId) {
        return null;
    }

    //TODO ПРОПАЛА РЕАЛИЗАЦИЯ
/*    @Override
    public List<TaskStage> getEmployeeLinkTaskForEmployee(Employee employee, Status status) {

        List<TaskStage> tasks = employeeLinkTaskRepository
                .findAllByEmployeeAndStatus(employee, status.name());

        log.info("[getEmployeeLinkTaskForEmployee] << result: {}", tasks);

        return tasks;
    }

    @Override
    public List<TaskStage> getAllTaskForCuratorChecking(Long curatorId) {

        List<Employee> curatorsEmployees = employeeService.getAllInternByCuratorId(curatorId);

        List<TaskStage> taskStages = new ArrayList<>();

        for (Employee intern : curatorsEmployees){
            taskStages.addAll(employeeLinkTaskRepository.findAllByEmployeeAndStatus(intern, Status.REVIEW.name()));
        }
        //TODO Create some DTO for response
        return taskStages;
    }

    @Override
    public TaskStage getEmployeeLinkTaskById(Long id) {
        log.info("[getEmployeeLinkTaskById] >> id: {}", id);

        TaskStage task = employeeLinkTaskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("TaskStage not found by this id :{} ", id);
                    return new ResourceNotFoundException("TaskStage not found by this id :: " + id);
                });

        log.info("[getEmployeeLinkTaskById] << result: {}", task);

        return task;
    }
    @Override
    public TaskStage setStatusTaskForEmployee(Long taskEmployeeId, Status status) {
        TaskStage taskStage = getEmployeeLinkTaskById(taskEmployeeId);

        taskStage.setStatus(status);

        return employeeLinkTaskRepository.save(taskStage);
    }*/


}
