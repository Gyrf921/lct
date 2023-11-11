package com.example.lct.util;

import com.example.lct.model.Employee;
import com.example.lct.model.TaskStage;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.service.EmailService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HistoryService;
import com.example.lct.service.StageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableAsync
@Component
@Slf4j
@RequiredArgsConstructor
public class DeadlineChecker {

    private final HistoryService historyService;
    private final EmailService emailService;
    private final StageService stageService;
    private final EmployeeService employeeService;

    @Async
    @Transactional
    @Scheduled(cron = "0 0 8 * * *") //В 08:00 каждого дня
    public void startChecking() {
        checkDeadline();
        log.info("///DeadlineChecker///startChecking///");
    }

    public void checkDeadline() {
        List<Employee> interns = employeeService.getAllIntern();
        for (Employee intern : interns) {
            sendMessageAboutDeadline(intern, checkDeadlineForTaskStages(intern, stageService.getAllTaskStageForEmployee(intern)));
        }
    }

    public List<TaskStage> checkDeadlineForTaskStages(Employee intern, List<TaskStage> taskStages) {
        List<TaskStage> taskStagesAfterDeadline = new ArrayList<>();
        for (TaskStage taskStage : taskStages) {
            if (taskStage.getDeadline().after(Timestamp.valueOf(LocalDateTime.now().minusDays(3)))) {
                taskStagesAfterDeadline.add(taskStage);
            }
            if (taskStage.getDeadline().after(Timestamp.valueOf(LocalDateTime.now()))) {
                historyService.createHistoryActionMiss(intern, HistoryType.DEADLINE, "Просрочен дедлайн для задачи: ".concat(taskStage.getTask().getName()));
            }
        }
        return taskStagesAfterDeadline;
    }

    public void sendMessageAboutDeadline(Employee intern, List<TaskStage> taskStages) {
        for (TaskStage taskStage : taskStages) {
            emailService.sendDeadlineEmail(intern, taskStage);
        }
    }
}
