package com.sparta.workflowhelper.domain.project.service;


import com.sparta.workflowhelper.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private ProjectRepository projectRepository ;

}
