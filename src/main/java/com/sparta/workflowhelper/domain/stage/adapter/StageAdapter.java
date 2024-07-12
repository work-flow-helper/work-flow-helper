package com.sparta.workflowhelper.domain.stage.adapter;

import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.project.repository.ProjectRepository;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.stage.repository.StageRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageAdapter {

    private final StageRepository stageRepository;

    private final ProjectRepository projectRepository;

    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(NotFoundErrorCode.NOT_FOUND_PROJECT_ENTITY.getMessage()));
    }

    public Stage save(Stage stage) {
        return stageRepository.save(stage);
    }

    public List<Stage> findStagesByProject(Project project) {
        return stageRepository.findByProjectOrderByPositionAsc(project);
    }
}
