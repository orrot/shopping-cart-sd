package com.orrot.store.common.rest.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DatabaseExceptionHandler {

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    protected SimpleErrorMessage handleDataIntegrityViolation(DataIntegrityViolationException exception) {

        log.error("Data integrity violation", exception);
        return new SimpleErrorMessage(
                "The data you are trying to insert violates the integrity of the database");
    }


}
