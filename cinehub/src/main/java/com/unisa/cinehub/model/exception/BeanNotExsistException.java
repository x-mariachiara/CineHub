package com.unisa.cinehub.model.exception;

public class BeanNotExsistException extends Throwable {
    public BeanNotExsistException() {
    }

    public BeanNotExsistException(String message) {
        super(message);
    }
}
