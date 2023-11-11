package com.example.lct.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PrepareMimeMessageException extends RuntimeException {

    public PrepareMimeMessageException(String message) {
        super(message);
    }
}
