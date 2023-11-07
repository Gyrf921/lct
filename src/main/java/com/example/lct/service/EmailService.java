package com.example.lct.service;

import com.example.lct.model.Employee;
import com.example.lct.model.Product;
import jakarta.mail.internet.MimeMessage;

public interface EmailService {
    void sendEmail(MimeMessage emailMessage);

    MimeMessage createBuyEmail(Employee employee, Product product);


    //MimeMessage createEmailMimeMessage(EmailInfo emailInfo);

}
