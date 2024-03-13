package com.trader.tradingplatform.configuration;

import jakarta.persistence.EntityNotFoundException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<GlobalError> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new GlobalError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    ResponseEntity<GlobalError> handleEntityNotFoundException(JdbcSQLIntegrityConstraintViolationException ex) throws JdbcSQLIntegrityConstraintViolationException {
        if (ex.getErrorCode() == 23505) {
            return new ResponseEntity<>(new GlobalError("Unique constraint violation"), HttpStatus.CONFLICT);
        }
        throw ex;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ValidationErrors(
                ex.getFieldErrors().stream()
                        .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList()
        ), HttpStatus.BAD_REQUEST);
    }

    record GlobalError(String errorMessage) { }
    record ValidationErrors(List<ValidationError> errors) { }
    record ValidationError(String fieldName, String message) { }
}
