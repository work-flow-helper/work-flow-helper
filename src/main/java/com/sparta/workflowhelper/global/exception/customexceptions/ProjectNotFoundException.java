package com.sparta.workflowhelper.global.exception.customexceptions;

import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;

public class ProjectNotFoundException extends NotFoundException {

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
