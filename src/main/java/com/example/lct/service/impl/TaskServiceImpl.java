package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Employee;
import com.example.lct.model.EmployeeLinkTask;
import com.example.lct.model.Post;
import com.example.lct.model.Task;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.model.factory.TaskFactory;
import com.example.lct.repository.EmployeeLinkTaskRepository;
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
    private final EmployeeLinkTaskRepository employeeLinkTaskRepository;
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
    public List<EmployeeLinkTask> getEmployeeLinkTaskForEmployee(Employee employee, Status status) {

        List<EmployeeLinkTask> tasks = employeeLinkTaskRepository
                .findAllByEmployeeAndStatus(employee, status.name());

        log.info("[getEmployeeLinkTaskForEmployee] << result: {}", tasks);

        return tasks;
    }

    @Override
    public List<EmployeeLinkTask> getAllTaskForCuratorChecking(Long curatorId) {

        List<Employee> curatorsEmployees = employeeService.getAllInternByCuratorId(curatorId);

        List<EmployeeLinkTask> employeeLinkTasks = new ArrayList<>();

        for (Employee intern : curatorsEmployees){
            employeeLinkTasks.addAll(employeeLinkTaskRepository.findAllByEmployeeAndStatus(intern, Status.REVIEW.name()));
        }
        //TODO Create some DTO for response
        return employeeLinkTasks;
    }

    @Override
    public EmployeeLinkTask getEmployeeLinkTaskById(Long id) {
        log.info("[getEmployeeLinkTaskById] >> id: {}", id);

        EmployeeLinkTask task = employeeLinkTaskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("EmployeeLinkTask not found by this id :{} ", id);
                    return new ResourceNotFoundException("EmployeeLinkTask not found by this id :: " + id);
                });

        log.info("[getEmployeeLinkTaskById] << result: {}", task);

        return task;
    }
    @Override
    public EmployeeLinkTask setStatusTaskForEmployee(Long taskEmployeeId, Status status) {
        EmployeeLinkTask employeeLinkTask = getEmployeeLinkTaskById(taskEmployeeId);

        employeeLinkTask.setStatus(status);

        return employeeLinkTaskRepository.save(employeeLinkTask);
    }


}
