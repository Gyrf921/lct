package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.Task;
import com.example.lct.model.TaskStage;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;

import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);

    List<Task> createTasks(Long companyId, TasksDTO tasksDTO);

    List<Task> createBaseTasks(Long companyId, TasksDTO tasksDTO);

    Task updateTaskInfo(Long taskId, TaskDTO taskDTO);

    List<Task> getBaseTasks(Long companyId);

    TaskStage getEmployeeLinkTaskById(Long id);

    TaskStage setStatusTaskForEmployee(Long taskEmployeeId, Status status);


    List<TaskStage> getEmployeeLinkTaskForEmployee(Employee employee, Status status);

    List<TaskStage> getAllTaskForCuratorChecking(Long curatorId);

    List<Task> getTasksByListId(List<Long> tasksId);
}
