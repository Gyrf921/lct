package com.example.lct.service.impl;

import com.example.lct.config.EmailPropertiesConfig;
import com.example.lct.config.EmailTemplateConfig;
import com.example.lct.model.Employee;
import com.example.lct.model.Product;
import com.example.lct.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailTemplateConfig emailTemplateConfig;
    private final EmailPropertiesConfig emailPropertiesConfig;

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Override
    public void sendEmail(MimeMessage emailMessage) {
        log.info("[sendEmail] >> emailMessage: {}", emailMessage);

        emailSender.send(emailMessage);

        log.info("[sendEmail] << result void");
    }

    @Override
    public MimeMessage createBuyEmail(Employee employee, Product product) {
        return null;
    }


}
