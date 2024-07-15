package com.sparta.workflowhelper.domain.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) //  null인 필드는 JSON 변환 시 무시
public class ProjectResponseDto {
    private final Long projectId;
    private final String title;
    private final String info;
    private final List<String> userName;

    public static ProjectResponseDto of(Long projectId, String title, String info) {
        return ProjectResponseDto.builder()
                .projectId(projectId)
                .title(title)
                .info(info)
                .build();
    }

    public static ProjectResponseDto memberOf(Long projectId, List<String> userName) {
        return ProjectResponseDto.builder()
                .projectId(projectId)
                .userName(userName)
                .build();
    }
}

