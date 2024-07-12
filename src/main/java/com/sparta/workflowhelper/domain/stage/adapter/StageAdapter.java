package com.sparta.workflowhelper.domain.stage.adapter;

import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.project.repository.ProjectRepository;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.stage.repository.StageRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectNotFoundException;
import com.sparta.workflowhelper.global.exception.customexceptions.StageNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageAdapter {

    private final StageRepository stageRepository;

    private final ProjectRepository projectRepository;

    // 프로젝트 ID가 존재하지 않으면 예외를 던짐
    public Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(NotFoundErrorCode.NOT_FOUND_PROJECT_ENTITY.getMessage()));
    }

    // Stage 엔티티를 데이터베이스에 저장
    public Stage save(Stage stage) {
        return stageRepository.save(stage);
    }

    // 프로젝트와 연관된 모든 스테이지를 위치 순서대로 찾음
    public List<Stage> findStagesByProject(Project project) {
        return stageRepository.findByProjectOrderByPositionAsc(project);
    }

    // 모든 스테이지를 찾음
    public List<Stage> findAll() {
        return stageRepository.findAll();
    }

    // 스테이지 ID가 존재하지 않으면 예외를 던짐
    public Stage findStageById(Long stageId) {
        return stageRepository.findById(stageId)
            .orElseThrow(() -> new StageNotFoundException(NotFoundErrorCode.NOT_FOUND_STAGE_ENTITY.getMessage()));
    }

}
