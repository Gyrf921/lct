package com.example.lct.service;

import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.TaskStage;
import com.example.lct.model.Task;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.TasksDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;

import java.util.List;

public interface HRService {
    List<Task> createTasksPlanForCompany(Company companyByUserPrincipal, TasksDTO tasksDTO);

    Task updateTaskInfoForCompany(Long taskId, TaskDTO taskDTO);

    Employee createInternForCompany(Company companyByUserPrincipal, EmployeeForCreateDTO employeeForCreateDTO);

    List<TaskStage> getAllTaskForCuratorChecking(Long curatorId);

    List<Employee> getAllInternForHR(Long employeeIdByUserPrincipal);

    List<Task> getAllTasksForCompany(Company companyByUserPrincipal);

    Employee createStageToIntern(StageDTO stageDTO);
}
