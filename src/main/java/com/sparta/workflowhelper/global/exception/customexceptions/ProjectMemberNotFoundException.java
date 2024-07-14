package com.sparta.workflowhelper.global.exception.customexceptions;

import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;

public class ProjectMemberNotFoundException extends NotFoundException {

    public ProjectMemberNotFoundException(String message) {
        super(message);
    }
}
