package com.sparta.workflowhelper.domain.card.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CardDetailResponseDto<T> {

    private final Long cardId;

    private final String title;

    private final String stageTitle;

    private final String content;

    private final LocalDateTime deadline;

    private final Integer position;

    private final List<T> workerInfoDtoList;

    public static <T> CardDetailResponseDto<T> of(Long cardId, String title, String stageTitle,
            String content, LocalDateTime deadline, Integer position,
            List<T> workerInfoDtoList) {
        return CardDetailResponseDto.<T>builder()
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
