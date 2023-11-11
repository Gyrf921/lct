package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.Stage;
import com.example.lct.model.Task;
import com.example.lct.model.TaskStage;
import com.example.lct.web.dto.request.hr.StageDTO;
import com.example.lct.web.dto.request.hr.StageWithoutTasksDTO;

import java.util.List;

public interface StageService {
    TaskStage getTaskStageById(Long id);

    Stage getStageById(Long id);

    Stage createBaseStageForIntern(Employee companyId);

    Stage createStageForIntern(Employee employee, StageDTO stageDTO);

    TaskStage setAnswerToTask(Long taskStageId, String answerUrl);

    List<TaskStage> getAllTaskStageForEmployee(Employee employee);

    List<TaskStage> getAllTaskStageForEmployeeByStageLevelDifficult(Employee employee, Integer stageLevelDifficult);

    Stage setTestToStage(Long stageId, String testUrl);

    List<Task> getTaskFromStage(Stage stage);

    Stage createStageForInternWithoutTask(Employee intern, StageWithoutTasksDTO stageWithoutTasksDTO);

    Stage setTaskToStage(Long stageId, Long taskId);
}
