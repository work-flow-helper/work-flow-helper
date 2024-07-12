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

    public ProjectMember findByUserIdAndProjectId(Long workerId, Long projectId) {
        return projectMemberRepository.findByUserIdAndProjectId(workerId, projectId)
                .orElseThrow(() -> new ProjectMemberNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_PROJECT_MEMBER_ENTITY.getMessage()));
    }

    public boolean existsByUserIdAndProjectId(Long workerId, Long projectId) {
        return projectMemberRepository.existsByUserIdAndProjectId(workerId, projectId);
    }
}
