package com.sparta.workflowhelper.global.exception.handler;

import com.sparta.workflowhelper.global.common.dto.CommonErrorResponseDto;
import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonErrorResponseDto<String>> handleNotFoundException(
            NotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonErrorResponseDto.of(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     * Controller 에서 @Valid 를 사용했을 때 규칙에 맞지 않았을 경우 발생하는 예외를 처리하는 핸들러
     *
     * @param e 유효성 검사에 실패했을 때 발생하는 예외
     * @return 유효성 검사에 실패한 필드의 에러 메시지 리스트
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonErrorResponseDto<List<String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonErrorResponseDto.of(HttpStatus.BAD_REQUEST.value(), errorMessages));
    }

}
