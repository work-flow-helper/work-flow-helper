package com.sparta.workflowhelper.domain.project.dto;

import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ProjectResponseDto {
    private final Long projectId;
    private final String title;
    private final String info;

    public static ProjectResponseDto of(Long projectId, String title, String info) {
        return ProjectResponseDto.builder()
                .projectId(projectId)
                .title(title)
                .info(info)
                .build();
    }
}
