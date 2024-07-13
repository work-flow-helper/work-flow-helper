package com.sparta.workflowhelper.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.workflowhelper.domain.worker.dto.WorkerInfoDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CardSimpleResponseDto {

    private final Long cardId;

    private final String title;

    private final String stageTitle;

    private final Integer position;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<WorkerInfoDto> workerInfoDtoList;

    public static CardSimpleResponseDto of(Long cardId, String title, String stageTitle,
            Integer position, List<WorkerInfoDto> workerInfoDtoList) {
        return CardSimpleResponseDto.builder()
                .cardId(cardId)
                .title(title)
                .stageTitle(stageTitle)
                .position(position)
                .workerInfoDtoList(workerInfoDtoList)
                .build();
    }

    public static CardSimpleResponseDto of(Long cardId, String title, String stageTitle,
            Integer position) {
        return CardSimpleResponseDto.builder()
                .cardId(cardId)
                .title(title)
                .stageTitle(stageTitle)
                .position(position)
                .build();
    }
}
