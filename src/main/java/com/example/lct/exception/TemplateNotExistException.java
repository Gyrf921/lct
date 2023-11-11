package com.example.lct.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TemplateNotExistException extends RuntimeException {

    public TemplateNotExistException(String message) {
        super(message);
    }
}