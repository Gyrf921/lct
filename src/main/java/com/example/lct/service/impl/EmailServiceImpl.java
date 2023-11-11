package com.example.lct.service.impl;

import com.example.lct.config.EmailPropertiesConfig;
import com.example.lct.exception.PrepareMimeMessageException;
import com.example.lct.exception.SetModelToEmailTemplateException;
import com.example.lct.exception.TemplateNotExistException;
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
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailPropertiesConfig emailPropertiesConfig;

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Override
    public void sendBuyEmail(String emailCurator, Employee buyer, Product product) {
        log.info("[sendBuyEmail] >> buyer: {}", buyer.getEmail());

        emailSender.send(prepareEmail(emailCurator,emailPropertiesConfig.getBuyTheme(),
                String.format(emailPropertiesConfig.getBuyTheme(), buyer.getName(), product.getName(), product.getCost())));

        log.info("[sendBuyEmail] << result void");
    }

    @Override
    public void sendCuratorCompleteTask(String emailCurator, Employee intern, TaskStage taskStage) {
        log.info("[sendCuratorCompleteTask] >> intern: {}", intern.getEmail());

        emailSender.send(prepareEmail(emailCurator, emailPropertiesConfig.getCompleteTaskTheme(),
                String.format(emailPropertiesConfig.getCompleteTaskText(), intern.getName(), taskStage.getTask().getName())));

        log.info("[sendCuratorCompleteTask] << result void");
    }

    @Override
    public void sendMarkToTaskByCurator(Employee intern, TaskStage taskStage) {
        log.info("[sendMarkToTaskByCurator] >> intern: {}", intern.getEmail());

        emailSender.send(prepareEmail(intern.getEmail(), emailPropertiesConfig.getCheckTaskTheme(),
                String.format(emailPropertiesConfig.getCheckTaskText(), taskStage.getStatus())));

        log.info("[sendMarkToTaskByCurator] << result void");
    }

    @Override
    public void sendDeadlineEmail(Employee intern, TaskStage taskStage) {
        log.info("[sendDeadlineEmail] >> intern: {}", intern.getEmail());

        emailSender.send(prepareEmail(intern.getEmail(), emailPropertiesConfig.getDeadlineTheme(),
                String.format(emailPropertiesConfig.getDeadlineText(), taskStage.getTask().getName())));

        log.info("[sendDeadlineEmail] << result void");
    }

    private MimeMessage prepareEmail(String emailForNotify, String theme, String text) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setFrom(emailFrom);
            helper.setTo(emailForNotify);
            helper.setSubject(theme);
            helper.setText(createEmailContent(theme, text), true);
        } catch (MessagingException e) {
            log.error("[prepareEmail] MessagingException -> PrepareMimeMessageException: {}", e.getMessage());
            throw new PrepareMimeMessageException(e.getMessage());
        }
        return mimeMessage;
    }

    private String createEmailContent(String theme, String text) {
        log.info("[EmailService|createEmailContent] >> theme: {}", theme);
        try {
            String emailContentInHtml = FreeMarkerTemplateUtils
                    .processTemplateIntoString(emailPropertiesConfig.templateEmail(), getDataMapForTemplate(theme, text));
            log.info("[EmailService|createEmailContent] << emailContentInHtml: {}", emailContentInHtml);
            return emailContentInHtml;
        } catch (TemplateException e) {
            log.error("[EmailService|createEmailContent] TemplateException -> SetModelToEmailTemplateException: {}", e.getMessage());
            throw new SetModelToEmailTemplateException(e.getMessage());
        } catch (IOException e) {
            log.error("[EmailService|createEmailContent] IOException -> TemplateNotExistException: {}", e.getMessage());
            throw new TemplateNotExistException(e.getMessage());
        }
    }

    private Map<String, Object> getDataMapForTemplate(String theme, String text) {
        log.info("[EmailService|getDataMapForTemplate] >> theme: {}", theme);

        Map<String, Object> model = new HashMap<>();

        model.put("theme", theme);
        model.put("text", text);

        log.info("[EmailService|getDataMapForTemplate] << result: {}", model);

        return model;
    }
}
