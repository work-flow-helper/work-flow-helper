package com.sparta.workflowhelper.global.exception.customexceptions;

import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(String message) {
        super(message);
    }

}
