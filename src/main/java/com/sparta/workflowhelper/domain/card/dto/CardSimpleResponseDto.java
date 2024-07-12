package com.sparta.workflowhelper.domain.card.dto;

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

    private final Integer position;

    private final List<WorkerInfoDto> workerInfoDtoList;

    public static CardSimpleResponseDto of(Long cardId, String title, Integer position,
            List<WorkerInfoDto> workerInfoDtoList) {
        return CardSimpleResponseDto.builder()
                .cardId(cardId)
                .title(title)
                .position(position)
                .workerInfoDtoList(workerInfoDtoList)
                .build();
    }
}
