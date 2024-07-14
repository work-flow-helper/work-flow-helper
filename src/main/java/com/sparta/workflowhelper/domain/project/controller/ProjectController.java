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

import java.util.List;

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

    //Get read MyProjects
    @GetMapping("/projects/search")
    public ResponseEntity<CommonResponseDto<List<ProjectResponseDto>>> readProjectsByUserId(
            @RequestParam Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseDto.of(
                        HttpStatus.OK.value(),
                        "사용자 프로젝트 조회",
                        projectService.readMyProjects(userId, userDetails)
                ));
    }

    //Get Read All Project
    @GetMapping("/projects")
    public ResponseEntity<CommonResponseDto<List<ProjectResponseDto>>> getAllProjects() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseDto.of(
                        HttpStatus.OK.value(),
                        "프로젝트 전체 조회",
                        projectService.readAllProjects()
                ));
    }

    //Put Update Project
    @PutMapping("/projects/{projectId}")
    public ResponseEntity<CommonResponseDto<ProjectResponseDto>> putProject(
            @PathVariable Long projectId,
            @RequestBody ProjectRequestDto projectRequestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseDto.of(
                        HttpStatus.OK.value(),
                        "프로젝트 수정",
                        projectService.updateProject(projectId, projectRequestDto)
                ));
    }

    //Delete Project
    @DeleteMapping("/api/projects/{projectId}")
    public ResponseEntity<CommonResponseDto<String>> deleteProject(@PathVariable Long projectId) {

        return ResponseEntity
                .status(204)
                .body(CommonResponseDto.of(
                        204,
                        "프로젝트 삭제",
                        projectService.deleteProject(projectId)
                ));
    }

    //Post add Participate User in Project
    public ProjectResponseDto userAddProject() {
        return null;
    }

    //Delete Removing Participating User in Project
}
