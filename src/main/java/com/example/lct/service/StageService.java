package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.Stage;
import com.example.lct.model.TaskStage;
import com.example.lct.web.dto.request.hr.StageDTO;

import java.util.List;

public interface StageService {
    TaskStage getTaskStageById(Long id);

    Stage createBaseStageForIntern(Employee companyId);

    Stage createStageForIntern(Employee employee, StageDTO stageDTO);

    TaskStage setAnswerToTask(Long taskStageId, String answerUrl);

    List<TaskStage> getAllTaskStageForEmployee(Employee employee);

    List<TaskStage> getAllTaskStageForEmployeeByStageLevelDifficult(Employee employee, Integer stageLevelDifficult);
}
