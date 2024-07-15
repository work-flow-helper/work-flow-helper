package com.sparta.workflowhelper.domain.stage.controller;

import com.sparta.workflowhelper.domain.stage.dto.StagePositionRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageResponseDto;
import com.sparta.workflowhelper.domain.stage.service.StageService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/stages")
public class StageController {

    private final StageService stageService;

    // 스테이지 생성(매니저만)
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<CommonResponseDto<StageResponseDto>> createdStage(@RequestBody StageRequestDto requestDto) {
        CommonResponseDto<StageResponseDto> responseDto = stageService.createdStage(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 스테이지 조회
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<CommonResponseDto<List<StageResponseDto>>> getAllStages(
        @PathVariable Long projectId) {
        CommonResponseDto<List<StageResponseDto>> responseDto = stageService.getAllStages(projectId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    // 스테이지 수정(매니저만)
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{stageId}")
    public ResponseEntity<CommonResponseDto<StageResponseDto>> updateStage(
        @PathVariable Long stageId,
        @RequestBody StageRequestDto requestDto) {
        CommonResponseDto<StageResponseDto> responseDto = stageService.updateStage(stageId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 스테이지 삭제(매니저만)
    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{stageId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteStage(@PathVariable Long stageId) {
        CommonResponseDto<Void> responseDto = stageService.deleteStage(stageId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 스테이지 순서 이동
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{stageId}/position")
    public ResponseEntity<CommonResponseDto<StageResponseDto>> moveStage(
        @PathVariable Long stageId,
        @RequestBody StagePositionRequestDto requestDto) {
        CommonResponseDto<StageResponseDto> responseDto = stageService.moveStage(stageId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
