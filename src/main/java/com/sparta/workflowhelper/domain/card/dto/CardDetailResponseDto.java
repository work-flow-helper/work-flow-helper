package com.sparta.workflowhelper.domain.card.dto;

import com.sparta.workflowhelper.domain.worker.dto.WorkQueryDto;
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

    private final String stageTitle;

    private final String content;

    private final LocalDateTime deadline;

    private final Integer position;

    private final List<WorkQueryDto> workerInfoDtoList;

    public static CardDetailResponseDto of(Long cardId, String title, String stageTitle,
            String content, LocalDateTime deadline, Integer position,
            List<WorkQueryDto> workerInfoDtoList) {
        return CardDetailResponseDto.builder()
                .cardId(cardId)
                .title(title)
                .stageTitle(stageTitle)
                .content(content)
                .deadline(deadline)
                .position(position)
                .workerInfoDtoList(workerInfoDtoList)
                .build();
    }
}
