package com.sparta.workflowhelper.global.exception.customexceptions;

public class AlreadyWithdrawnException extends RuntimeException{

    public AlreadyWithdrawnException(String message) {
        super(message);
    }
}
