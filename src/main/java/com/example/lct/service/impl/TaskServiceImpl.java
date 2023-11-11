package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Post;
import com.example.lct.model.Task;
import com.example.lct.model.factory.TaskFactory;
import com.example.lct.repository.TaskRepository;
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
    private static final int LEVEL_FOR_BASE = 0;

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
    public Task createTask(Long companyId, TaskDTO taskDTO) {
        return taskRepository.save(TaskFactory.create(companyId, taskDTO));
    }

    @Override
    public List<Task> createTasks(Long companyId, List<TaskDTO> tasksDTO) {
        List<Task> tasks = new ArrayList<>();

        for (TaskDTO taskDTO : tasksDTO) {
            tasks.add(TaskFactory.create(companyId, taskDTO));
        }

        log.info("[createTasks] << result: {}", tasks);

        return taskRepository.saveAll(tasks);
    }

    @Override
    public List<Task> createBaseTasks(Long companyId, TasksDTO tasksDTO) {

        List<Task> tasks = new ArrayList<>();

        for (TaskDTO taskDTO : tasksDTO.getTaskDTOList()) {
            tasks.add(TaskFactory.createBase(companyId, taskDTO));
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

    public List<Task> getTasks(Long companyId, Post post, Integer levelDifficult) {

        List<Task> tasks = taskRepository
                .findAllByCompanyIdAndPostAndLevelDifficulty(companyId, post, levelDifficult);

        log.info("[getTasks] << result: {}", tasks);

        return tasks;
    }

    @Override
    public List<Task> getBaseTasks(Long companyId) {

        List<Task> tasks = taskRepository.findAllByCompanyId(companyId).stream()
                .filter(task -> task.getLevelDifficulty().equals(LEVEL_FOR_BASE)).toList();

        log.info("[getBaseTasks] << result: {}", tasks);

        return tasks;
    }

    @Override
    public List<Task> getTasksByListId(List<Long> tasksId) {
        List<Task> tasks = new ArrayList<>();
        for (Long taskId: tasksId) {
            tasks.add(getTaskById(taskId));
        }
        return tasks;
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }



}
