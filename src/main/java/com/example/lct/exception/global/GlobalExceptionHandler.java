package com.example.lct.exception.global;

import com.example.lct.exception.InsufficientFundsException;
import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.exception.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


/*    @ExceptionHandler(BadLoginOrPasswordException.class)
    public ResponseEntity<?> badLoginOrPasswordException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNAUTHORIZED.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_BAD_LOGIN_PASSWORD, LocalDate.now(), ex.getMessage(), request.getDescription(true));

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }*/

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_VALIDATE, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
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

/*    @ExceptionHandler(TokenLifetimeExpiredException.class)
    public ResponseEntity<?> tokenLifetimeExpiredException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_LIFETIME_TOKEN, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }*/


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ConstantErrorCode.BUSINESS_ERROR_CODE_SERVER, LocalDate.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
