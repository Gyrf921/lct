package com.example.lct.exception.global;

import com.example.lct.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.security.SignatureException;
import java.time.LocalDate;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_VALIDATE, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SetModelToEmailTemplateException.class)
    public ResponseEntity<?> setModelToEmailTemplateException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_EMAIL, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TemplateNotExistException.class)
    public ResponseEntity<?> templateNotExistException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_EMAIL, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(PrepareMimeMessageException.class)
    public ResponseEntity<?> prepareMimeMessageException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_EMAIL, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> insufficientFundsException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_VALIDATE, LocalDate.now(), ex.getMessage(), request.getDescription(true));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> userAlreadyExistException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_VALIDATE, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_VALIDATE, LocalDate.now(), "User is not exist|" + ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenLifetimeExpiredException.class)
    public ResponseEntity<?> tokenLifetimeExpiredException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_LIFETIME_TOKEN, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateMimeMessageException.class)
    public ResponseEntity<?> createMimeMessageException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_SERVER, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_SERVER, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> signatureException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_JWT, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
