package com.sparta.workflowhelper.global.exception.customexceptions;

import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;

public class CardNotFoundException extends NotFoundException {

    public CardNotFoundException(String message) {
        super(message);
    }
}
