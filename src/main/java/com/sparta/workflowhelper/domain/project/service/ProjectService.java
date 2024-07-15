package com.sparta.workflowhelper.domain.project.service;


import com.sparta.workflowhelper.domain.mapping.adapter.ProjectMemberAdapter;
import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.domain.mapping.repository.ProjectMemberRepository;
import com.sparta.workflowhelper.domain.project.adapter.ProjectAdapter;
import com.sparta.workflowhelper.domain.project.dto.ProjectRequestDto;
import com.sparta.workflowhelper.domain.project.dto.ProjectResponseDto;
import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.project.repository.ProjectRepository;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.domain.user.repository.UserRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.globalexceptions.UserAlreadyExistsException;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;

import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectAdapter projectAdapter;
    private final ProjectMemberAdapter projectMemberAdapter;
    private final UserAdapter userAdapter;

    // 유저 아이디를 받아서 멤버스에 추가하는 로직 추가해야됌 24.07.12
    // 했지만 이해해야됌
    @Transactional

    public ProjectResponseDto createdProject(ProjectRequestDto projectRequestDto, User user) {
        Project project = Project.createdProject(projectRequestDto.getTitle(),
                projectRequestDto.getInfo());
        Project savedProject = projectAdapter.projectSave(project);
        List<ProjectMember> projectMemberList = new ArrayList<>();
        ProjectMember projectMember = ProjectMember.of(user, savedProject);
        projectMemberList.add(projectMember);
        Set<Long> addedUserIds = new HashSet<>();
        if (projectRequestDto.getUserIdList() != null) {
            for (Long userId : projectRequestDto.getUserIdList()) { // 팀을 만들때 추가해서 넣어주는 아이디들
                if (Objects.equals(userId, user.getId()) || addedUserIds.contains(userId)) {
                    continue;
                }
                User participatingUser = userAdapter.findById(userId);
                ProjectMember saveProjectMember = ProjectMember.of(participatingUser, project);
                projectMemberList.add(saveProjectMember);
                addedUserIds.add(userId); // 아이디를 추가된 목록에 추가
            }
        } // 중복 아이디 추가 불가능
        projectMemberAdapter.saveAll(projectMemberList);
        // if 구문으로 널값이 오면 useridlist에 본인 id값만 넣어주고 널값이아니면 포문을 돌
        return ProjectResponseDto.of(project.getId(), project.getTitle(), project.getInfo());
    }

    public ProjectResponseDto findProject(Long projectId) {
        Project project = projectAdapter.findById(projectId);
        return ProjectResponseDto.of(project.getId(), project.getTitle(), project.getInfo());
    }

    public List<ProjectResponseDto> readMyProjects(Long userId, UserDetailsImpl userDetails) {
        return projectAdapter.findMyProjects(userId, userDetails);
    }


    public List<ProjectResponseDto> readAllProjects() {
        return projectAdapter.findAllProjects();
    }

    @Transactional
    public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectRequestDto) {
        Project findProject = projectAdapter.findById(projectId);
        findProject.changeOf(projectRequestDto.getTitle(), projectRequestDto.getInfo());
        projectAdapter.save(findProject);
        return ProjectResponseDto.of(findProject.getId(), findProject.getTitle(),
                findProject.getInfo());
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectAdapter.deletedProjectMethod(projectId);
    }

    public ProjectResponseDto addProjectMember(Long projectId, UserDetailsImpl userDetails) {
        Project project = projectAdapter.findById(projectId);
        User user = userAdapter.findById(userDetails.getUser().getId());

        // 이미 프로젝트에 참가한 유저인지 확인
        if (projectMemberAdapter.existsByProjectAndUser(project, user)) {
            throw new UserAlreadyExistsException("해당하는 유저는 이미 프로젝트에 참가중입니다.");
        }
        ProjectMember projectMember = ProjectMember.of(user, project);
        projectMemberAdapter.save(projectMember);
        return ProjectResponseDto.memberOf(projectId, user.getNickname());
    }

    public void deleteProjectMember(Long projectId, Long memberId) {
        ProjectMember projectMember = projectMemberAdapter.findByProjectIdAndUserId(projectId, memberId);
        projectMemberAdapter.delete(projectMember);
    }
}
