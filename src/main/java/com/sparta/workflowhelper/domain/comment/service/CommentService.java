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
import jakarta.transaction.Transactional;
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
}
