package com.sparta.workflowhelper.domain.stage.service;

import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.dto.StageRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageResponseDto;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageAdapter stageAdapter;

    @Transactional
    public StageResponseDto createdStage(StageRequestDto requestDto) {
        var project = stageAdapter.findProjectById(requestDto.getProjectId());
        List<Stage> stages = stageAdapter.findStagesByProject(project);

        int newPosition = calculateNewPosition(stages);

        Stage stage = new Stage(requestDto.getTitle(), newPosition, project);

        Stage savedStage = stageAdapter.save(stage);

        return new StageResponseDto(
            savedStage.getId(),
            savedStage.getTitle(),
            savedStage.getPosition(),
            savedStage.getProject().getId()
        );
    }

    private int calculateNewPosition(List<Stage> stages) {
        if (stages.isEmpty()) {
            return 1;
        } else {
            int lastPosition = stages.get(stages.size() - 1).getPosition();
            return lastPosition + 1;
        }
    }
}
