package com.example.lct.service.impl;

import com.example.lct.model.Employee;
import com.example.lct.model.TaskStage;
import com.example.lct.service.InternService;
import com.example.lct.service.StageService;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternServiceImpl implements InternService {


    private final StageService stageService;

    @Override
    public Boolean setAnswerToTask(Long taskStageId, TasksToCheckDTO answer) {
        stageService.setAnswerToTask(taskStageId, answer.getAnswerUrl());
        return true;
    }

    @Override
    public TaskStage getTaskStageById(Long taskStageId) {
        return stageService.getTaskStageById(taskStageId);
    }

    @Override
    public List<TaskStage> getTasksStageForEmployee(Employee employee) {
        return stageService.getAllTaskStageForEmployee(employee);
    }

    @Override
    public List<TaskStage> getTasksStagesForEmployeeByLevelDifficult(Employee employee, Integer stageLevelDifficult) {
        return stageService.getAllTaskStageForEmployeeByStageLevelDifficult(employee, stageLevelDifficult);
    }

}
