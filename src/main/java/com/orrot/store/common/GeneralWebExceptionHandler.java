package com.orrot.store.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GeneralWebExceptionHandler {

    // TODO THIS

//    // Helps to convert the exception message to a more user-friendly message depending on the containing of the exception message
//    @ExceptionHandler({AnyException.class})
//    @ResponseStatus(BAD_REQUEST)
//    public ErrorResponse handleBadRequestException(ServiceException exception) {
//        return ErrorResponse.builder()
//                .message(exception.getMessage())
//                .messageToDisplay(exception.getMessage())
//                .build();
//    }
//
//    @ExceptionHandler({Throwable.class, IOException.class, MapperException.class})
//    @ResponseStatus(INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleThrowable(Throwable exception) {
//        log.error("Unhandled error has occurred", exception);
//        return ErrorResponse.builder()
//                .message(exception.getMessage())
//                .messageToDisplay(exception.getMessage())
//                .build();
//    }

}
