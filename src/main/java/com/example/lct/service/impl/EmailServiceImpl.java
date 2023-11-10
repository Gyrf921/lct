package com.example.lct.service.impl;

import com.example.lct.config.EmailPropertiesConfig;
import com.example.lct.exception.CreateMimeMessageException;
import com.example.lct.model.Employee;
import com.example.lct.model.Product;
import com.example.lct.model.TaskStage;
import com.example.lct.service.EmailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailPropertiesConfig emailPropertiesConfig;
    private final freemarker.template.Configuration configurationEmail;
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Override
    @Async
    public void sendEmail(MimeMessage emailMessage) {
        log.info("[sendEmail] >> emailMessage: {}", emailMessage);

        emailSender.send(emailMessage);

        log.info("[sendEmail] << result void");
    }

    @Override
    public void sendDeadlineMessage(Employee intern, TaskStage taskStage) {
        log.info("[sendDeadlineMessage] >> intern: {}, taskStage: {}", intern, taskStage);

        emailSender.send(createDeadlineEmail(intern, taskStage));

        log.info("[sendEmail] << result void");
    }

    @Override
    public MimeMessage createBuyEmail(Employee employee, Product product) {
        return null;
    }

    private MimeMessage createDeadlineEmail(Employee intern, TaskStage taskStage) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(emailFrom);
            helper.setTo(intern.getEmail());
            helper.setSubject(emailPropertiesConfig.getDeadlineTheme() + taskStage.getTask().getName());

            String emailContent = getDeadlineEmailContent(
                    emailPropertiesConfig.getDeadlineTheme());
            helper.setText(emailContent, true);

        } catch (MessagingException | TemplateException | IOException e) {
            log.error("Error when creating a email");
            throw new CreateMimeMessageException("Error when creating a email, exception's message: " + e.getMessage());
        }

        return mimeMessage;
    }

    private String getDeadlineEmailContent(String theme) throws IOException, TemplateException {
        log.info("[getDeadlineEmailContent] >> theme: {}", theme);

        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();

        model.put("theme", theme);

        configurationEmail.getTemplate("email-deadline.ftlh").process(model, stringWriter);

        String emailContent = stringWriter.getBuffer().toString();

        log.info("[getDeadlineEmailContent] << result: {}", emailContent);

        return emailContent;
    }
}
