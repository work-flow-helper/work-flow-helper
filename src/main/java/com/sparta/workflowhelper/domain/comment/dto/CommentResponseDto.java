package com.sparta.workflowhelper.domain.comment.dto;

import com.sparta.workflowhelper.domain.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CommentResponseDto {

    private final Long commentId;

    private final String writer;

    private final String content;

    private CommentResponseDto(Long commentId, String writer, String content) {
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;
    }

    public static CommentResponseDto of(Long commentId, String writer, String content) {
        return CommentResponseDto.builder()
            .commentId(commentId)
            .writer(writer)
            .content(content)
            .build();
    }

    public static CommentResponseDto from(Comment comment) {
        return of(comment.getId(), comment.getUser().getNickname(), comment.getContent());
    }
}
