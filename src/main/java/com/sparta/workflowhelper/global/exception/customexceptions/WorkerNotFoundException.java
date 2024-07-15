package com.sparta.workflowhelper.global.exception.customexceptions;

import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;

public class WorkerNotFoundException extends NotFoundException {

    public WorkerNotFoundException(String message) {
        super(message);
    }
}
