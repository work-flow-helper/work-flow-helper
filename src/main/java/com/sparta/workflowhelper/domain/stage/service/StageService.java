package com.sparta.workflowhelper.domain.stage.service;

import com.sparta.workflowhelper.domain.project.adapter.ProjectAdapter;
import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.dto.StagePositionRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageResponseDto;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageAdapter stageAdapter;

    private final ProjectAdapter projectAdapter;

    @Transactional
    public CommonResponseDto<StageResponseDto> createdStage(StageRequestDto requestDto) {
        Project project = projectAdapter.findById(requestDto.getProjectId());
        List<Stage> stageList = stageAdapter.findStagesByProject(project);

        int newPosition = calculateNewPosition(stageList);

        Stage stage = Stage.createdStage(requestDto.getTitle(), newPosition, project);
        Stage savedStage = stageAdapter.save(stage);

        StageResponseDto responseDto = StageResponseDto.from(savedStage);
        return new CommonResponseDto<>(201, "스테이지 등록", responseDto);
    }

    @Transactional(readOnly = true)
    public CommonResponseDto<List<StageResponseDto>> getAllStages(Long projectId) {
        List<Stage> stages = stageAdapter.findAllByProjectId(projectId);
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
    public CommonResponseDto<StageResponseDto> moveStage(Long stageId,
            StagePositionRequestDto requestDto) {

        Stage stage = stageAdapter.findStageById(stageId);
        Project project = stage.getProject();
        List<Stage> stages = stageAdapter.findStagesByProject(project);

        int newPosition = requestDto.getPosition();

        // 새로운 포지션이 유효한지 검사
        if (newPosition < 1 || newPosition > stages.size()) {
            throw new IllegalArgumentException("잘못된 위치입니다.");
        }

        updateStagePositions(stages, stage, newPosition);

        StageResponseDto responseDto = StageResponseDto.from(stage);

        return new CommonResponseDto<>(200, "스테이지 위치 수정", responseDto);
    }

    private void updateStagePositions(List<Stage> stages, Stage movedStage, int newSequenceNum) {
        int currentSequenceNumber = movedStage.getPosition();

        if (newSequenceNum == currentSequenceNumber) {
            return;
        }

        // 포지션 이동
        if (newSequenceNum < currentSequenceNumber) {
            for (Stage stage : stages) {
                if (stage.getPosition() >= newSequenceNum
                        && stage.getPosition() < currentSequenceNumber) {
                    stage.updatePosition(stage.getPosition() + 1);
                    stageAdapter.save(stage);
                }
            }
        } else {
            for (Stage stage : stages) {
                if (stage.getPosition() > currentSequenceNumber
                        && stage.getPosition() <= newSequenceNum) {
                    stage.updatePosition(stage.getPosition() - 1);
                    stageAdapter.save(stage);
                }
            }
        }

        movedStage.updatePosition(newSequenceNum);
        stageAdapter.save(movedStage);
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
