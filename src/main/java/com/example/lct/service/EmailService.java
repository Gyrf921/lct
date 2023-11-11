package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.Product;
import com.example.lct.model.TaskStage;
import jakarta.mail.internet.MimeMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendBuyEmail(String emailCurator, Employee employee, Product product);

    @Async
    void sendCuratorCompleteTask(String emailCurator, Employee intern, TaskStage taskStage);

    @Async
    void sendMarkToTaskByCurator(Employee intern, TaskStage taskStage);

    @Async
    void sendDeadlineEmail(Employee intern, TaskStage taskStage);
}
