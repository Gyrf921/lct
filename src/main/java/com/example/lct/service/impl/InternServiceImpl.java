package com.example.lct.service.impl;

import com.example.lct.model.Employee;
import com.example.lct.model.Stage;
import com.example.lct.model.Task;
import com.example.lct.model.TaskStage;
import com.example.lct.service.EmailService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.InternService;
import com.example.lct.service.StageService;
import com.example.lct.web.dto.request.intern.TasksToCheckDTO;
import com.example.lct.web.dto.response.StageResponseDTO;
import com.example.lct.web.dto.response.TaskForCheckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InternServiceImpl implements InternService {

    private final EmployeeService employeeService;
    private final StageService stageService;
    private final EmailService emailService;



    @Override
    public TaskStage setAnswerToTask(Employee intern, Long taskStageId, TasksToCheckDTO answer) {

        TaskStage taskStage = stageService.setAnswerToTask(taskStageId, answer.getAnswerUrl());

        Employee curator = employeeService.getEmployeeById(intern.getCuratorId());

        emailService.sendCuratorCompleteTask(curator.getEmail(), intern, taskStage);
        return taskStage;
    }

    @Override
    public TaskStage getTaskStageById(Long taskStageId) {
        return stageService.getTaskStageById(taskStageId);
    }

    @Override
    public List<StageResponseDTO> getStageForEmployee(Employee employee) {
        List<StageResponseDTO> responseDTOS = new ArrayList<>();
        for (Stage stage: employee.getStages()) {
            List<Task> tasksInStage = stageService.getTaskFromStage(stage);

            responseDTOS.add(new StageResponseDTO(stage, tasksInStage));
        }

        return responseDTOS;
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
