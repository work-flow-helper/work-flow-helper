package com.sparta.workflowhelper.domain.project.service;


import com.sparta.workflowhelper.domain.mapping.adapter.ProjectMemberAdapter;
import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.domain.project.adapter.ProjectAdapter;
import com.sparta.workflowhelper.domain.project.dto.ProjectRequestDto;
import com.sparta.workflowhelper.domain.project.dto.ProjectResponseDto;
import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private ProjectAdapter projectAdapter;
    private ProjectMemberAdapter projectMemberAdapter;
    private UserAdapter userAdapter;

    public ProjectService(ProjectAdapter projectAdapter) {
        this.projectAdapter = projectAdapter;
    }


    // 유저 아이디를 받아서 멤버스에 추가하는 로직 추가해야됌 24.07.12
    // 했지만 이해해야됌
    @Transactional
    public ProjectResponseDto createdProject(ProjectRequestDto projectRequestDto, User user) {
        Project project = Project.createdProject(projectRequestDto.getTitle(), projectRequestDto.getInfo());
        projectAdapter.projectSave(project);
        List<ProjectMember> projectMemberList = new ArrayList<>();
        ProjectMember projectMember = ProjectMember.of(user, project);
        projectMemberList.add(projectMember);
        for (Long userId : projectRequestDto.getUserIdList()) {
            User participatingUser = userAdapter.findById(userId);
            ProjectMember saveProjectMember = ProjectMember.of(participatingUser, project);
            projectMemberList.add(saveProjectMember);
        }
        projectMemberAdapter.saveAll(projectMemberList);
        return ProjectResponseDto.of(project.getId(), project.getTitle(), project.getInfo());
    }

    public ProjectResponseDto findProject(Long projectId) {
        Project project = projectAdapter.findById(projectId);
        return ProjectResponseDto.of(project.getId(), project.getTitle(), project.getInfo());
    }

    public List<ProjectResponseDto> readMyProjects(Long userId, UserDetailsImpl userDetails) {
        return projectAdapter.findMyprojects(userId, userDetails);
    }


    public List<ProjectResponseDto> readAllProjects() {
        return projectAdapter.findAllProjects();
    }
}
