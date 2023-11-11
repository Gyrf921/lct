package com.example.lct.service;

import com.example.lct.model.*;
import com.example.lct.web.dto.request.admin.obj.EmployeeForCreateDTO;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.StageWithoutTasksDTO;
import com.example.lct.web.dto.request.hr.TestDTO;
import com.example.lct.web.dto.request.hr.obj.TaskDTO;
import com.example.lct.web.dto.response.EmployeeTeamResponseDTO;
import com.example.lct.web.dto.response.StageResponseDTO;
import com.example.lct.web.dto.response.TaskForCheckDTO;

import java.util.List;

public interface CuratorService {

    List<TaskForCheckDTO> getTaskStagesForCuratorChecking(Long curatorId);

    List<Task> createTasks(Company company, List<TaskDTO> tasksDTO);

    List<Task> createTask(Company companyByUserPrincipal, TaskDTO taskDTO);

    List<Task> updateTask(Company companyByUserPrincipal, Long taskId, TaskDTO taskDTO);

    Employee createIntern(Company companyByUserPrincipal, EmployeeForCreateDTO employeeForCreateDTO);

    List<EmployeeTeamResponseDTO> getCuratorInterns(Long employeeIdByUserPrincipal);

    List<Task> getAllTasksForCompany(Company companyByUserPrincipal);

    List<StageResponseDTO> createStageToIntern(Long internId, StageDTO stageDTO);

    List<StageResponseDTO> createStageToInternWithoutTask(Long internId, StageWithoutTasksDTO stageWithoutTasksDTO);

    Stage setTestToStage(Long stageId, TestDTO testDTO);

    void evaluateInternAnswer(Long intern, Long taskId, Boolean isAccepted);

    List<StageResponseDTO> setTaskToStage(Long stageId, Long taskId);


}
