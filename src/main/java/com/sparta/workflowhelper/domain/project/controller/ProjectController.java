package com.sparta.workflowhelper.domain.project.controller;


import com.sparta.workflowhelper.domain.project.dto.ProjectRequestDto;
import com.sparta.workflowhelper.domain.project.dto.ProjectResponseDto;
import com.sparta.workflowhelper.domain.project.service.ProjectService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    //Post Create New Project
    @PostMapping("/projects")
    public ResponseEntity<CommonResponseDto<ProjectResponseDto>> createNewProject(
            @RequestBody ProjectRequestDto projectRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponseDto
                        .of(
                                HttpStatus.CREATED.value(),
                                "프로젝트 등록",
                                projectService.createdProject(projectRequestDto, userDetails.getUser())));
    }

    //Get Read Only One Project
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<CommonResponseDto<ProjectResponseDto>> readOnlyProject(
            @PathVariable Long projectId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseDto.of(
                        HttpStatus.OK.value(),
                        "프로젝트 단일 조회",
                        projectService.findProject(projectId)
                ));
    }

    //Get Read All Project
    public ProjectResponseDto readAllProject() {
        return null;
    }

    //Put Update Project
    public ProjectResponseDto updateProject() {
        return null;
    }

    //Delete Soft Delete Project
    public ProjectResponseDto deleteProject() {
        return null;
    }

    //Post User Participate Project
    public ProjectResponseDto userAddProject() {
        return null;
    }

    //Delete Hard Delete Participating User
    public ProjectResponseDto userDeleteProject() {
        return null;
    }
}
