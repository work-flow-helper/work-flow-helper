package com.sparta.workflowhelper.domain.card.dto;

import com.sparta.workflowhelper.domain.worker.dto.WorkerInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CardDetailResponseDto {

    private final Long cardId;

    private final String title;

    private final String content;

    private final LocalDateTime deadline;

    private final List<WorkerInfoDto> workerInfoDtoList;

    public static CardDetailResponseDto of(Long cardId, String title, String content,
            LocalDateTime deadline, List<WorkerInfoDto> workerInfoDtoList) {
        return CardDetailResponseDto.builder()
                .cardId(cardId)
                .title(title)
                .content(content)
                .deadline(deadline)
                .workerInfoDtoList(workerInfoDtoList)
                .build();
    }
}
