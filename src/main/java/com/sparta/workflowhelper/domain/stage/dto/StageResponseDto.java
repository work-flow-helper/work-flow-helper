package com.sparta.workflowhelper.domain.stage.dto;

import com.sparta.workflowhelper.domain.stage.entity.Stage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class StageResponseDto {

    private final Long stageId;

    private final String title;

    public static StageResponseDto of(Long stageId, String title) {
        return StageResponseDto.builder()
            .stageId(stageId)
            .title(title)
            .build();
    }

    public static StageResponseDto from(Stage stage) {
        return of(stage.getId(), stage.getTitle());
    }

}
