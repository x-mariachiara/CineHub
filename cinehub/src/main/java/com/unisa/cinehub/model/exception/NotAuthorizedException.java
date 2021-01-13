package com.unisa.cinehub.model.exception;

public class NotAuthorizedException extends Throwable {
    public NotAuthorizedException() {
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
