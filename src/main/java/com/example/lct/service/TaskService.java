package com.example.lct.service;

import com.example.lct.model.Task;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;

import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);

    List<Task> createTasks(Long companyId, List<TaskDTO> tasksDTO);

    Task createTask(Long companyId, TaskDTO taskDTO);

    List<Task> createBaseTasks(Long companyId, TasksDTO tasksDTO);

    Task updateTaskInfo(Long taskId, TaskDTO taskDTO);

    List<Task> getBaseTasks(Long companyId);


    List<Task> getTasksByListId(List<Long> tasksId);

    void deleteTask(Task task);

}
