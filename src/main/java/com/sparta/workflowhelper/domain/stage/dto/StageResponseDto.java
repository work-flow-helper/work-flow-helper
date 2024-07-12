package com.sparta.workflowhelper.domain.stage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StageResponseDto {

    private Long stageId;

    private String title;

    public StageResponseDto(Long stageId, String title) {
        this.stageId = stageId;
        this.title = title;
    }

}
