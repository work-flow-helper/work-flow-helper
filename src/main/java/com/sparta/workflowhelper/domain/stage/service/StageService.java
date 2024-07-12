package com.sparta.workflowhelper.domain.stage.service;

import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.dto.StageRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageResponseDto;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StageService {
    private final StageAdapter stageAdapter;

    @Transactional
    public CommonResponseDto<StageResponseDto> createdStage(StageRequestDto requestDto) {
        var project = stageAdapter.findProjectById(requestDto.getProjectId());
        List<Stage> stages = stageAdapter.findStagesByProject(project);

        int newPosition = calculateNewPosition(stages);

        Stage stage = Stage.createdStage(requestDto.getTitle(), newPosition, project);
        Stage savedStage = stageAdapter.save(stage);

        StageResponseDto responseDto = new StageResponseDto(savedStage.getId(), savedStage.getTitle());
        return new CommonResponseDto<>(201, "스테이지 등록", responseDto);
    }

    @Transactional
    public CommonResponseDto<List<StageResponseDto>> getAllStages() {
        List<Stage> stages = stageAdapter.findAll();
        List<StageResponseDto> responseDtos = stages.stream()
            .map(stage -> new StageResponseDto(stage.getId(), stage.getTitle()))
            .collect(Collectors.toList());

        return new CommonResponseDto<>(200, "스테이지 조회", responseDtos);
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
