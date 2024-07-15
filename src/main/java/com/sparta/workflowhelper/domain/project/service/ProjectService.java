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
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectAdapter projectAdapter;
    private final ProjectRepository projectRepository;
    private final ProjectMemberAdapter projectMemberAdapter;
    private final UserAdapter userAdapter;
    private final UserRepository userRepository;

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

        if (projectRequestDto.getUserIdList() == null) {
            User participatingUser = userAdapter.findById(user.getId());
            ProjectMember saveProjectMember = ProjectMember.of(participatingUser, project);
            projectMemberList.add(saveProjectMember);
        } else {
            for (Long userId : projectRequestDto.getUserIdList()) { // 팀을 만들때 추가해서 넣어주는 아이디들
                User participatingUser = userAdapter.findById(userId);
                ProjectMember saveProjectMember = ProjectMember.of(participatingUser, project);
                projectMemberList.add(saveProjectMember);
            }
            projectMemberAdapter.saveAll(projectMemberList);
        }
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
        projectRepository.save(findProject);
        return ProjectResponseDto.of(findProject.getId(), findProject.getTitle(),
                findProject.getInfo());
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectAdapter.deletedProjectMethod(projectId);
    }

    public ProjectResponseDto addProjectMember(Long projectId, UserDetailsImpl userDetails) {
        Project project = projectAdapter.findById(projectId);
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RuntimeException("해당 유저 없음")); // Exception 변경 리펙토링 내용 **
        ProjectMember projectMember = ProjectMember.of(user, project);
        projectMemberRepository.save(projectMember);
        return ProjectResponseDto.memberOf(projectId, user.getNickname());
    }

    public void deleteProjectMember(Long projectId, Long memberId) {
        ProjectMember projectMember = projectMemberRepository.findByProjectIdAndUserId(projectId,
                        memberId)
                .orElseThrow(() -> new RuntimeException("해당 유저 없음"));// Exception 변경 리펙토링 내용 **
        projectMemberRepository.delete(projectMember);
    }
}
