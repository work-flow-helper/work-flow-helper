package com.sparta.workflowhelper.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private String content;

    private Long cardId;

    public static CommentRequestDto of(String content, Long cardId) {
        CommentRequestDto dto = new CommentRequestDto();
        dto.content = content;
        dto.cardId = cardId;
        return dto;
    }

}
