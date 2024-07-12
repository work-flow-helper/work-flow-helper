package com.sparta.workflowhelper.domain.stage.service;

import com.sparta.workflowhelper.domain.project.entity.Project;
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
        Project project = stageAdapter.findProjectById(requestDto.getProjectId());
        List<Stage> stageList = stageAdapter.findStagesByProject(project);

        int newPosition = calculateNewPosition(stageList);

        Stage stage = Stage.createdStage(requestDto.getTitle(), newPosition, project);
        Stage savedStage = stageAdapter.save(stage);

        StageResponseDto responseDto = StageResponseDto.from(savedStage);
        return new CommonResponseDto<>(201, "스테이지 등록", responseDto);
    }

    @Transactional
    public CommonResponseDto<List<StageResponseDto>> getAllStages() {
        List<Stage> stages = stageAdapter.findAll();
        List<StageResponseDto> responseDtoList = stages.stream()
            .map(StageResponseDto::from)
            .collect(Collectors.toList());

        return new CommonResponseDto<>(200, "스테이지 조회", responseDtoList);
    }

    @Transactional
    public CommonResponseDto<StageResponseDto> updatedStage(Long stageId,
        StageRequestDto requestDto) {
        Stage stage = stageAdapter.findStageById(stageId);
        stage.updatedStage(requestDto.getTitle());
        Stage updatedStage = stageAdapter.save(stage);

        StageResponseDto responseDto = StageResponseDto.from(updatedStage);

        return new CommonResponseDto<>(200, "스테이지 수정", responseDto);
    }

    @Transactional
    public CommonResponseDto<Void> deletedStage(Long stageId) {
        Stage stage = stageAdapter.findStageById(stageId);
        stageAdapter.deletedStage(stage);
        return new CommonResponseDto<>(200, "스테이지 삭제", null);
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
