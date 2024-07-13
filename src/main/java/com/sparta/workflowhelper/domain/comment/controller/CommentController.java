package com.sparta.workflowhelper.domain.comment.controller;

import com.sparta.workflowhelper.domain.comment.dto.CommentRequestDto;
import com.sparta.workflowhelper.domain.comment.dto.CommentResponseDto;
import com.sparta.workflowhelper.domain.comment.service.CommentService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommonResponseDto<CommentResponseDto>> createdComment(
        @RequestBody CommentRequestDto requestDto,
        @RequestParam Long userId) {
        CommonResponseDto<CommentResponseDto> responseDto = commentService.createdComment(requestDto, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
