package com.sparta.workflowhelper.domain.comment.service;

import com.sparta.workflowhelper.domain.card.adapter.CardAdapter;
import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.comment.adapter.CommentAdapter;
import com.sparta.workflowhelper.domain.comment.dto.CommentRequestDto;
import com.sparta.workflowhelper.domain.comment.dto.CommentResponseDto;
import com.sparta.workflowhelper.domain.comment.entity.Comment;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentAdapter commentAdapter;

    private UserAdapter userAdapter;

    private CardAdapter cardAdapter;

    @Transactional
    public CommonResponseDto<CommentResponseDto> createdComment(CommentRequestDto requestDto, Long userId) {
        User user = userAdapter.findById(userId);
        Card card = cardAdapter.findById(requestDto.getCardId());
        Comment comment = Comment.create(requestDto.getContent(), user, card);
        Comment savedComment = commentAdapter.save(comment);

        CommentResponseDto responseDto = CommentResponseDto.from(savedComment);
        return CommonResponseDto.of(201, "댓글 등록", responseDto);
    }

    @Transactional(readOnly = true)
    public CommonResponseDto<List<CommentResponseDto>> getComment() {
        List<Comment> comments = commentAdapter.findAll();
        List<CommentResponseDto> responseDtos = comments.stream()
            .map(CommentResponseDto::from)
            .collect(Collectors.toList());

        return CommonResponseDto.of(200, "댓글 전체 조회", responseDtos);
    }

    @Transactional
    public CommonResponseDto<CommentResponseDto> updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentAdapter.findById(commentId);
        comment.updateContent(requestDto.getContent());
        Comment updatedComment = commentAdapter.save(comment);

        CommentResponseDto responseDto = CommentResponseDto.from(updatedComment);
        return CommonResponseDto.of(200, "댓글 수정", responseDto);
    }

    @Transactional
    public CommonResponseDto<Void> deleteComment(Long commentId) {
        Comment comment = commentAdapter.findById(commentId);
        commentAdapter.delete(comment);

        return CommonResponseDto.of(204, "댓글 삭제 완료");
    }
}
