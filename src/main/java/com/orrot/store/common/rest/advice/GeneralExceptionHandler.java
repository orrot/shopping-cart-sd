package com.orrot.store.common.rest.advice;


import com.orrot.store.common.exception.BusinessRuleException;
import com.orrot.store.common.exception.GeneralShoppingCartException;
import com.orrot.store.common.exception.UnExistingEntityException;
import com.orrot.store.common.exception.UnExistingRelationshipException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Hidden
public class GeneralExceptionHandler {

    public static final String MESSAGE_DELIMITER = ", ";

    @ExceptionHandler(UnExistingEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleErrorMessage handleGeneralBadRequest(GeneralShoppingCartException exception) {
        return new SimpleErrorMessage(exception.getMessage());
    }

    @ExceptionHandler({ UnExistingRelationshipException.class, BusinessRuleException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleErrorMessage handleNotFound(GeneralShoppingCartException exception) {
        return new SimpleErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleErrorMessage handleConstraintViolation(ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.collectingAndThen(
                        Collectors.joining(MESSAGE_DELIMITER),
                        SimpleErrorMessage::new));
    }


    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleErrorMessage handleThrowable(Throwable exception) {
        log.error("Unhandled error has occurred", exception);
        return new SimpleErrorMessage(exception.getMessage());
    }

}
