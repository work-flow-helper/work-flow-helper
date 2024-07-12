package com.sparta.workflowhelper.domain.card.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardDetailQueryDto {

    private final Long cardId;

    private final String title;

    private final String stageTitle;

    private final String content;

    private final LocalDateTime deadline;

    private final Integer position;
}
