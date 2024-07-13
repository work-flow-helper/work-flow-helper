package com.sparta.workflowhelper.domain.stage.service;

import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.dto.StagePositionRequestDto;
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
    public CommonResponseDto<StageResponseDto> updateStage(Long stageId,
        StageRequestDto requestDto) {
        Stage stage = stageAdapter.findStageById(stageId);
        stage.updateStage(requestDto.getTitle());
        Stage updatedStage = stageAdapter.save(stage);

        StageResponseDto responseDto = StageResponseDto.from(updatedStage);

        return new CommonResponseDto<>(200, "스테이지 수정", responseDto);
    }

    @Transactional
    public CommonResponseDto<Void> deleteStage(Long stageId) {
        Stage stage = stageAdapter.findStageById(stageId);
        stageAdapter.deleteStage(stage);
        return new CommonResponseDto<>(200, "스테이지 삭제", null);
    }

    @Transactional
    public CommonResponseDto<StageResponseDto> moveStage(Long stageId, StagePositionRequestDto requestDto) {
        Stage stage = stageAdapter.findStageById(stageId);
        Project project = stage.getProject();
        List<Stage> stages = stageAdapter.findStagesByProject(project);

        int newPosition = requestDto.getPosition();
        if (newPosition < 1 || newPosition > stages.size()) {
            throw new IllegalArgumentException("잘못된 위치입니다.");
        }

        stages.remove(stage);
        stages.add(newPosition - 1, stage);

        for (int i = 0; i < stages.size(); i++) {
            stages.get(i).updatePosition(i + 1);
            stageAdapter.save(stages.get(i));
        }

        StageResponseDto responseDto = StageResponseDto.from(stage);
        return new CommonResponseDto<>(200, "스테이지 위치 수정", responseDto);
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
