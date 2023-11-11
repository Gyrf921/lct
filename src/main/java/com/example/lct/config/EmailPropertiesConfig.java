package com.example.lct.config;

import freemarker.template.Template;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Data
public class EmailPropertiesConfig {
    private final freemarker.template.Configuration configurationEmail;
    @Bean
    public Template templateEmail() throws IOException {
        return configurationEmail.getTemplate("email.ftlh");
    }

    String buyTheme = "Уведомление о покупке сотрудника";
    String buyText = "Уведомление о недавней покупке товара сотрудником  %s. Был приобретён товар :  %s, за  %d.";

    String deadlineTheme = "Дедлайн близко!!!";
    String deadlineText = "Выходит время для сдачи задания: %s, успейте сделать его до изтечения срока.";

    String completeTaskTheme = "Уведомление о выполнении задачи";
    String completeTaskText = "Пользователь %s , выполнил задание ' %s ', и его нужно проверить!";

    String checkTaskTheme = "Вашу задачу проверили!";
    String checkTaskText = "Куратор внимательно проверил ваш ответ и поставил статус: %s";

}
