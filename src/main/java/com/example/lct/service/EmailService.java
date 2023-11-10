package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.Product;
import com.example.lct.model.TaskStage;
import jakarta.mail.internet.MimeMessage;

public interface EmailService {
    void sendEmail(MimeMessage emailMessage);

    MimeMessage createBuyEmail(Employee employee, Product product);

    void sendDeadlineMessage(Employee intern, TaskStage taskStage);


    //MimeMessage createEmailMimeMessage(EmailInfo emailInfo);

}
