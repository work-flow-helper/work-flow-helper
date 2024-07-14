package com.sparta.workflowhelper.domain.project.adapter;

import com.sparta.workflowhelper.domain.project.dto.ProjectResponseDto;
import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectAdapter {
    private final ProjectRepository projectRepository;

    public void projectSave(Project project) {
        projectRepository.save(project);
    }

    public Project findById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() ->
                new RuntimeException("해당 프로젝트가 없습니다."));
    } // Exception 변경 리펙토링 내용 **

    ;
}
// 좀 충격먹었어요 이게 created? 아닌데 SSS급 created
// 1. 정적메서드 -> 이거때문에 5배정도 더 어렵게 느껴지는거같음
// 2. 내일 이거 혼자 복습하면서 리뷰하는데 시간좀 들거같은데