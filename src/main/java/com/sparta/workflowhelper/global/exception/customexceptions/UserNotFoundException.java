package com.sparta.workflowhelper.global.exception.customexceptions;

import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
