package com.unisa.cinehub.model.exception;

public class AlreadyExsistsException extends Throwable {
    public AlreadyExsistsException() {
    }

    public AlreadyExsistsException(String message) {
        super(message);
    }
}
