package com.unisa.cinehub.model.exception;

public class UserUnderAgeException extends Throwable {

    public UserUnderAgeException() {
    }

    public UserUnderAgeException(String message) {
        super(message);
    }
}
