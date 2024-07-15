package com.sparta.workflowhelper.domain.comment.controller;

import com.sparta.workflowhelper.domain.comment.dto.CommentRequestDto;
import com.sparta.workflowhelper.domain.comment.dto.CommentResponseDto;
import com.sparta.workflowhelper.domain.comment.service.CommentService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommonResponseDto<CommentResponseDto>> createdComment(
        @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonResponseDto<CommentResponseDto> responseDto = commentService.createdComment(requestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 댓글 전체 조회
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<CommentResponseDto>>> getComment() {
        CommonResponseDto<List<CommentResponseDto>> responseDto = commentService.getComment();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto<CommentResponseDto>> updateComment(
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonResponseDto<CommentResponseDto> responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonResponseDto<Void> responseDto = commentService.deleteComment(commentId, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.NO_CONTENT);
    }
}
