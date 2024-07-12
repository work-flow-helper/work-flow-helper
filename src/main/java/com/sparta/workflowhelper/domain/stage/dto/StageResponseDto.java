package com.sparta.workflowhelper.domain.stage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StageResponseDto {

    private Long id;

    private String title;

    private Integer position;

    private Long projectId;

    public StageResponseDto(Long id, String title, Integer position, Long projectId) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.projectId = projectId;
    }

}
