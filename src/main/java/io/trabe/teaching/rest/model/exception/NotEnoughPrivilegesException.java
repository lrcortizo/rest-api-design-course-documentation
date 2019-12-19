package io.trabe.teaching.rest.model.exception;

public class NotEnoughPrivilegesException extends RuntimeException {

    public NotEnoughPrivilegesException(String message) {
        super(message);
    }

    public NotEnoughPrivilegesException(String message, Throwable cause) {
        super(message, cause);
    }
}
