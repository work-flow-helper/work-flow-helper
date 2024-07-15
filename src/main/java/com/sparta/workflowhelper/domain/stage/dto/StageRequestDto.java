package com.sparta.workflowhelper.domain.stage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StageRequestDto {

    private Long projectId;

    private String title;

    // 생성자 오버로딩
    public StageRequestDto(String title) {
        this.title = title;
    }
}
