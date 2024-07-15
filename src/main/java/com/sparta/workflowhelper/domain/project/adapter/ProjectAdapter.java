package com.sparta.workflowhelper.domain.project.adapter;

import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.domain.mapping.repository.ProjectMemberRepository;
import com.sparta.workflowhelper.domain.project.dto.ProjectResponseDto;
import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.project.repository.ProjectRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.CustomAccessDeniedException;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectNotFoundException;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectAdapter {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public Project projectSave(Project project) {
        return projectRepository.save(project);
    }

    public Project findById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() ->
                new ProjectNotFoundException("해당 프로젝트가 없습니다."));
    }

    public List<ProjectResponseDto> findMyProjects(Long userId, UserDetailsImpl userDetails) {
        if (!userId.equals(userDetails.getUser().getId())) {
            throw new CustomAccessDeniedException("권한이 없습니다.");  // Exception 변경 리펙토링 내용 **
        } // 로그인한 유저와 요청하는 유저의 프로젝트 정보들이 다를 때
//Project members에서 해당하는 유저 아이디에 해당하는 프로젝트 아이디를 찾아서 리스트에 추가
        List<ProjectMember> myProjects = projectMemberRepository.findByUserIdOrderByCreatedAtDesc(
                userId);
        return myProjects
                .stream()
                .map(e -> ProjectResponseDto.of(e.getProject().getId(), e.getProject().getTitle(),
                        e.getProject().getInfo()))
                .collect(Collectors.toList()); //stream 처리중에 리스트로 수집하는데 이용
        // 변환 매핑 필터링등의 연산 수행한 결과를 리스트 형태로 반환하고자 할 때 씀
        //  .collect(Collectors.toList());  는 스트림의 요소를 수집하여 새로운 리스트를 생성하는 컬렉터
    }

    public List<ProjectResponseDto> findAllProjects() {
        return projectRepository // 맵을 이용해서 정적매서드를 통한 생성자역할이라 보면 된다.
                .findAll()
                .stream()
                .map(e -> ProjectResponseDto.of(e.getId(), e.getTitle(), e.getInfo()))
                .collect(Collectors.toList());
    }

    public void deletedProjectMethod(Long projectId) {
        try {
            projectRepository.deleteById(projectId);
        } catch (EmptyResultDataAccessException e) {   // Exception 변경 리펙토링 내용 **
            throw new ProjectNotFoundException("해당 프로젝트가 없습니다." + projectId);
        }
    }

}