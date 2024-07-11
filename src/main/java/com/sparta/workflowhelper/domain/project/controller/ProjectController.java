package com.sparta.workflowhelper.domain.project.controller;


import com.sparta.workflowhelper.domain.project.dto.ProjectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {

    //Post Create New Project
public ProjectResponseDto createNewProject(){
    return null;
}
    //Get Read Only One Project
    public ProjectResponseDto readOnlyProject(){
        return null;
    }
    //Get Read All Project
    public ProjectResponseDto readAllProject(){
        return null;
    }
    //Put Update Project
    public ProjectResponseDto updateProject(){
        return null;
    }
    //Delete Soft Delete Project
    public ProjectResponseDto deleteProject(){
        return null;
    }
    //Post User Participate Project
    public ProjectResponseDto userAddProject(){
        return null;
    }
    //Delete Hard Delete Participating User
    public ProjectResponseDto userDeleteProject(){
        return null;
    }
}
