package com.sparta.workflowhelper.domain.project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class ProjectRequestDto {
    @NotNull
    private String title;

    @Size(max = 200)
    private String info;

    private List<Long> userIdList;
    //리퀘스트에서 유저정보를 받음(id) 리스트로
}
