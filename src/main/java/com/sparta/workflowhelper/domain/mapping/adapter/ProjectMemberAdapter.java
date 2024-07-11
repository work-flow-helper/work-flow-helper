package com.sparta.workflowhelper.domain.mapping.adapter;

import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.domain.mapping.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectMemberAdapter {
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectMember save(ProjectMember projectMember) {
        return projectMemberRepository.save(projectMember);
    }

    public void saveAll(List<ProjectMember> projectMemberList) {
        projectMemberRepository.saveAll(projectMemberList);
    }
}
