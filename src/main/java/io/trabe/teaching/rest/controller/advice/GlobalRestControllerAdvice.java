package io.trabe.teaching.rest.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.trabe.teaching.rest.model.exception.NotAuthenticatedException;
import io.trabe.teaching.rest.model.exception.NotEnoughPrivilegesException;
import io.trabe.teaching.rest.model.pojo.api.external.common.ErrorInformation;

@RestControllerAdvice(basePackages = "io.trabe.teaching.rest.controller.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(GlobalRestControllerAdvice.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInformation handleExceptions(Throwable t) {
        log.error("Internal error", t);
        return new ErrorInformation(t.getClass().getSimpleName());
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorInformation handleUnauthorizedUsers(Exception e) {
        return new ErrorInformation(e.getMessage());
    }

    @ExceptionHandler(NotEnoughPrivilegesException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorInformation handleNotEnoughPrivilegesException(NotEnoughPrivilegesException e) {
        return new ErrorInformation(e.getMessage());
    }
}
