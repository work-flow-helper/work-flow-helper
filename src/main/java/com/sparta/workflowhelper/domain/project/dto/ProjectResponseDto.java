package com.sparta.workflowhelper.domain.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) //  null인 필드는 JSON 변환 시 무시
public class ProjectResponseDto {
    private final Long projectId;
    private final String title;
    private final String info;
    private final String userNickName;

    public static ProjectResponseDto of(Long projectId, String title, String info) {
        return ProjectResponseDto.builder()
                .projectId(projectId)
                .title(title)
                .info(info)
                .build();
    }

    public static ProjectResponseDto memberOf(Long projectId, String userNickName) {
        return ProjectResponseDto.builder()
                .projectId(projectId)
                .userNickName(userNickName)
                .build();
    }
}

