package com.sparta.workflowhelper.domain.stage.adapter;

import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.stage.repository.StageRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.StageNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageAdapter {

    private final StageRepository stageRepository;

    // Stage 엔티티를 데이터베이스에 저장
    public Stage save(Stage stage) {
        return stageRepository.save(stage);
    }

    // 프로젝트 내의 스테이지를 위치 순으로 조회
    public List<Stage> findStagesByProject(Project project) {
        return stageRepository.findByProjectOrderByPositionAsc(project);
    }

    // 스테이지 ID가 존재하지 않으면 예외를 던짐
    public Stage findStageById(Long stageId) {
        return stageRepository.findById(stageId)
                .orElseThrow(() -> new StageNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_STAGE_ENTITY.getMessage()));
    }

    // 스테이지 삭제
    public void deleteStage(Stage stage) {
        stageRepository.delete(stage);
    }

    public Stage findById(Long stageId) {
        return stageRepository.findById(stageId)
                .orElseThrow(() -> new StageNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_STAGE_ENTITY.getMessage()));
    }

    public List<Stage> findAllByProjectId(Long projectId) {
        return stageRepository.findAllByProjectIdOrderByPositionAsc(projectId);
    }
}
