package com.sparta.workflowhelper.domain.mapping.adapter;

import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.domain.mapping.repository.ProjectMemberRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectMemberNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectMemberAdapter {

    private final ProjectMemberRepository projectMemberRepository;

    public ProjectMember findById(Long userId) {
        return projectMemberRepository.findById(userId)
                .orElseThrow(() -> new ProjectMemberNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_PROJECT_MEMBER_ENTITY.getMessage()));
    }

    public boolean existsById(Long userId) {
        return projectMemberRepository.existsById(userId);
    }

    public ProjectMember findByIdAndProjectId(Long workerId, Long projectId) {
        return projectMemberRepository.findByIdAndProjectId(workerId, projectId)
                .orElseThrow(() -> new ProjectMemberNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_PROJECT_MEMBER_ENTITY.getMessage()));
    }

    public boolean existsByIdAndProjectId(Long workerId, Long projectId) {
        return projectMemberRepository.existsByIdAndProjectId(workerId, projectId);
    }
}
