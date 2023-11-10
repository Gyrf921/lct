package com.example.lct.service;

import com.example.lct.model.*;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;

import java.util.List;

public interface CuratorService {

    List<TaskStage> getTaskStagesForCuratorChecking(Long curatorId);
    List<Task> createTasksForCompany(Company companyByUserPrincipal, TasksDTO tasksDTO);

    List<Task> createBaseTasksForCompany(Company companyByUserPrincipal, TasksDTO tasksDTO);

    Employee createIntern(Company companyByUserPrincipal, EmployeeForCreateDTO employeeForCreateDTO);

    List<Employee> getAllInternForHR(Long employeeIdByUserPrincipal);

    List<Task> getAllTasksForCompany(Company companyByUserPrincipal);

    Employee createStageToIntern(Long internId, StageDTO stageDTO);

    Stage setTestToStage(Long stageId, TestDTO testDTO);

}
