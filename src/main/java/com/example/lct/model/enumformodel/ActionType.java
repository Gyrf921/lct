package com.example.lct.model.enumformodel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ActionType {
    CREATE("Создание"),
    READ("Просмотр"),
    UPDATE("Обновление"),
    DELETE("Удаление"),
    MISS("Пропуск"),
    OTHER("Действие с");

    private final String value;
}
