package com.cinehub.cinehub.utils;

public class NotAuthorizatedException extends Exception{
    public NotAuthorizatedException() {
    }

    public NotAuthorizatedException(String message) {
        super(message);
    }
}
