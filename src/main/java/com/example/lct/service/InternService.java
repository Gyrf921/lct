package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.Stage;
import com.example.lct.model.TaskStage;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;
import com.example.lct.web.dto.response.StageResponseDTO;

import java.util.List;

public interface InternService {


    TaskStage getTaskStageById(Long taskStageId);
    List<StageResponseDTO> getStageForEmployee(Employee employeeById);

    List<TaskStage> getTasksStageForEmployee(Employee employee);

    List<TaskStage> getTasksStagesForEmployeeByLevelDifficult(Employee employee, Integer stageLevelDifficult);


    TaskStage setAnswerToTask(Employee intern, Long taskStageId, TasksToCheckDTO answer);

}
