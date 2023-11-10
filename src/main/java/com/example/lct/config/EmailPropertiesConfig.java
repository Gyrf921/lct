package com.example.lct.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EmailPropertiesConfig {
    String buyTheme = "Уведомление о покупке сотрудника";
    String deadlineTheme = "Выходит время сдачи задания: ";
    String buyText = "Уведомление о недавней покупке товара сотрудником ${employeeName}. Был приобретён товар : ${productName}, за ${productCost}.";
}
