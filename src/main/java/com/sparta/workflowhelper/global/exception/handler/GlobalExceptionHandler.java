package com.sparta.workflowhelper.global.exception.handler;

import com.sparta.workflowhelper.global.common.dto.CommonErrorResponseDto;
import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonErrorResponseDto> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonErrorResponseDto.of(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

}
