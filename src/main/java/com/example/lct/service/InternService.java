package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.TaskStage;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;

import java.util.List;

public interface InternService {


    TaskStage getTaskStageById(Long taskStageId);

    List<TaskStage> getTasksStageForEmployee(Employee employee);

    List<TaskStage> getTasksStagesForEmployeeByLevelDifficult(Employee employee, Integer stageLevelDifficult);


    TaskStage setAnswerToTask(Employee intern, Long taskStageId, TasksToCheckDTO answer);
}
