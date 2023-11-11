package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.Product;
import com.example.lct.model.TaskStage;
import jakarta.mail.internet.MimeMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendBuyEmail(String emailCurator, Employee employee, Product product);
}
