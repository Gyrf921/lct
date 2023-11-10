package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Employee;
import com.example.lct.model.Stage;
import com.example.lct.model.Task;
import com.example.lct.model.TaskStage;
import com.example.lct.model.enumformodel.Status;
import com.example.lct.repository.StageRepository;
import com.example.lct.repository.TaskStageRepository;
import com.example.lct.service.StageService;
import com.example.lct.service.TaskService;
import com.example.lct.web.dto.request.hr.StageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StageServiceImpl implements StageService {

    private final TaskService taskService;
    private final StageRepository stageRepository;
    private final TaskStageRepository taskStageRepository;
    private static final int LEVEL_FOR_BASE_STAGE = 0;
    private static final String NAME_FOR_BASE_STAGE = "Общие задачи";

    @Override
    public TaskStage getTaskStageById(Long id) {
        log.info("[getTaskStageById] >> id: {}", id);

        TaskStage task = taskStageRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("TaskStage not found by this id :{} ", id);
                    return new ResourceNotFoundException("TaskStage not found by this id :: " + id);
                });

        log.info("[getTaskStageById] << result: {}", task);

        return task;
    }

    @Override
    public Stage getStageById(Long id) {
        log.info("[getStageById] >> id: {}", id);

        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Stage not found by this id :{} ", id);
                    return new ResourceNotFoundException("Stage not found by this id :: " + id);
                });

        log.info("[getStageById] << result: {}", stage);

        return stage;
    }

    @Override
    public Stage createBaseStageForIntern(Employee employee) {

        List<Task> tasks = taskService.getBaseTasks(employee.getCompanyId());

        Stage stage = stageRepository.save(Stage.builder()
                .companyId(employee.getCompanyId())
                .employeeId(employee.getEmployeeId())
                .name(NAME_FOR_BASE_STAGE)
                .levelDifficulty(LEVEL_FOR_BASE_STAGE)
                .status(Status.OPENED)
                .deadline(Timestamp.valueOf(LocalDateTime.now().plusMonths(1))).build());

        List<TaskStage> taskStages = new ArrayList<>();
        for (Task task : tasks) {
            taskStages.add(TaskStage.builder()
                    .stage(stage)
                    .task(task)
                    .status(Status.OPENED)
                    .deadline(Timestamp.valueOf(LocalDateTime.now().plusMonths(1))).build());
        }
        taskStageRepository.saveAll(taskStages);
        log.info("[createBaseStageForIntern] << result: {}", stage);

        return stage;
    }

    @Override
    public Stage createStageForIntern(Employee employee, StageDTO stageDTO) {
        List<Task> tasks = taskService.getTasksByListId(stageDTO.getTasksId());

        Stage stage = stageRepository.save(Stage.builder()
                .companyId(employee.getCompanyId())
                .employeeId(employee.getEmployeeId())
                .name(stageDTO.getName())
                .levelDifficulty(stageDTO.getLevelDifficulty())
                .status(Status.OPENED)
                .deadline(Timestamp.valueOf(stageDTO.getDeadline())).build());

        List<TaskStage> taskStages = new ArrayList<>();
        for (Task task : tasks) {
            taskStages.add(TaskStage.builder()
                    .stage(stage)
                    .task(task)
                    .status(Status.OPENED)
                    .deadline(task.getDeadline()).build());
        }
        taskStageRepository.saveAll(taskStages);

        return stage;
    }

    public TaskStage setAnswerToTask(Long taskStageId, String answer) {
        //TODO проверка дедлайна
        TaskStage taskStage = getTaskStageById(taskStageId);

        taskStage.setAnswerUrl(answer);

        return taskStageRepository.save(taskStage);
    }

    @Override
    public List<TaskStage> getAllTaskStageForEmployee(Employee employee) {
        List<TaskStage> taskStage = new ArrayList<>();

        for (Stage stage : employee.getStages()) {
            taskStage.addAll(taskStageRepository.findAllByStage(stage));
        }
        return taskStage;
    }

    @Override
    public List<TaskStage> getAllTaskStageForEmployeeByStageLevelDifficult(Employee employee, Integer stageLevelDifficult) {
        return getAllTaskStageForEmployee(employee).stream()
                .filter(taskStage -> taskStage.getStage().getLevelDifficulty().equals(stageLevelDifficult)).toList();
    }

    @Override
    public Stage setTestToStage(Long stageId, String testUrl) {
        Stage stage = getStageById(stageId);
        stage.setTestUrl(testUrl);
        return stageRepository.save(stage);
    }

}
