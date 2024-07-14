package com.sparta.workflowhelper.domain.card.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CardPositionRequestDto {

    @NotNull(message = "스테이지 고유번호는 공백일 수 없습니다.")
    private Long stageId;

    @NotNull(message = "새로운 순서번호는 공백일 수 없습니다.")
    private Integer newPosition;
}
