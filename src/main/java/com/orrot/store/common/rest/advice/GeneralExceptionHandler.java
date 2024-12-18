package com.orrot.store.common.rest.advice;


import com.orrot.store.common.exception.BusinessRuleException;
import com.orrot.store.common.exception.GeneralShoppingCartException;
import com.orrot.store.common.exception.UnExistingEntityException;
import com.orrot.store.common.exception.UnExistingRelationshipException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(value = {BusinessRuleException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleErrorMessage handleBusinessRulesException(BusinessRuleException exception) {
        return Optional.of(exception)
                .filter(BusinessRuleException::hasBrokenRules)
                .map(BusinessRuleException::getBrokenRules)
                .map(brokenRules -> new SimpleErrorMessage("%s. %s".formatted(
                        exception.getMessage(), StringUtils.join(brokenRules, ", "))))
                .orElse(new SimpleErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(value = {UnExistingEntityException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleErrorMessage handleGeneralBadRequest(GeneralShoppingCartException exception) {
        return new SimpleErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(value = {UnExistingRelationshipException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleErrorMessage handleNotFound(GeneralShoppingCartException exception) {
        return new SimpleErrorMessage(exception.getMessage());
    }


    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleErrorMessage handleThrowable(Throwable exception) {
        log.error("Unhandled error has occurred", exception);
        return new SimpleErrorMessage(exception.getMessage());
    }

}
