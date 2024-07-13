package com.sparta.workflowhelper.global.exception.customexceptions;

public class UserDuplicateException extends RuntimeException{

    public UserDuplicateException(String message) {
        super(message);
    }

}
