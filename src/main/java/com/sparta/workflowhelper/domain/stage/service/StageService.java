package com.sparta.workflowhelper.domain.stage.service;

import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.dto.StageRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageResponseDto;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageAdapter stageAdapter;

    // Stage 등록
    @Transactional
    public StageResponseDto createdStage(StageRequestDto requestDto) {
        var project = stageAdapter.findProjectById(requestDto.getProjectId());
        List<Stage> stages = stageAdapter.findStagesByProject(project);

        int newPosition = calculatedNewPosition(stages);

        Stage stage = new Stage(requestDto.getTitle(), newPosition, project);

        Stage savedStage = stageAdapter.save(stage);

        return new StageResponseDto(
            savedStage.getId(),
            savedStage.getTitle(),
            savedStage.getPosition(),
            savedStage.getProject().getId()
        );
    }

    // Stage 전체 조회
    @Transactional
    public List<StageResponseDto> getAllStages() {
        List<Stage> stages = stageAdapter.findAll();
        return stages.stream()
            .map(stage -> new StageResponseDto(
                stage.getId(),
                stage.getTitle(),
                stage.getPosition(),
                stage.getProject().getId()))
            .collect(Collectors.toList());
    }

    // Stage의 Position 계산
    private int calculatedNewPosition(List<Stage> stages) {
        if (stages.isEmpty()) {
            return 1;
        } else {
            int lastPosition = stages.get(stages.size() - 1).getPosition();
            return lastPosition + 1;
        }
    }
}
