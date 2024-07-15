package com.sparta.workflowhelper.domain.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardSimpleQueryDto {

    private final Long cardId;

    private final String title;

    private final String stageTitle;

    private final Integer position;
}
