package com.sparta.workflowhelper.domain.mapping.adapter;

import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.domain.mapping.repository.ProjectMemberRepository;
import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectMemberNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectMemberAdapter {
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectMember findById(Long userId) {
        return projectMemberRepository.findById(userId)
                .orElseThrow(() -> new ProjectMemberNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_PROJECT_MEMBER_ENTITY.getMessage()));
    }

    public ProjectMember save(ProjectMember projectMember) {
        return projectMemberRepository.save(projectMember);
    }

    public void saveAll(List<ProjectMember> projectMemberList) {
        projectMemberRepository.saveAll(projectMemberList);
    }

    public boolean existsByProjectAndUser(Project project, User user) {
        return projectMemberRepository.existsByProjectAndUser(project, user);
    }

    public void delete(ProjectMember projectMember) {
        projectMemberRepository.delete(projectMember);
    }


    public ProjectMember findByProjectIdAndUserId(Long projectId, Long memberId) {
        return projectMemberRepository.findByProjectIdAndUserId(projectId,
                memberId).orElseThrow(() -> new RuntimeException("해당 유저 없음"));
    }
}
