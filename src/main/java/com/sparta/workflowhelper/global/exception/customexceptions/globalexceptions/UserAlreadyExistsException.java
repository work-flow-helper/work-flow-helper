package com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
