package com.sparta.workflowhelper.global.exception.customexceptions;

import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;

public class StageNotFoundException extends NotFoundException {

    public StageNotFoundException(String message) {
        super(message);
    }
}
