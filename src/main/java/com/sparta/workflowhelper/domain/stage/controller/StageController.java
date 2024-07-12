package com.sparta.workflowhelper.domain.stage.controller;

import com.sparta.workflowhelper.domain.stage.dto.StageRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageResponseDto;
import com.sparta.workflowhelper.domain.stage.service.StageService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/stages")
public class StageController {

    private final StageService stageService;

    // 스테이지 생성
    @PostMapping
    public ResponseEntity<CommonResponseDto<StageResponseDto>> createStage(@RequestBody StageRequestDto requestDto) {
        CommonResponseDto<StageResponseDto> responseDto = stageService.createdStage(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 스테이지 조회
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<StageResponseDto>>> getAllStages() {
        CommonResponseDto<List<StageResponseDto>> responseDto = stageService.getAllStages();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    // 스테이지 수정
    @PutMapping("/{stageId}")
    public ResponseEntity<CommonResponseDto<StageResponseDto>> updatedStage(
        @PathVariable Long stageId,
        @RequestBody StageRequestDto requestDto) {
        CommonResponseDto<StageResponseDto> responseDto = stageService.updatedStage(stageId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 스테이지 삭제
    @DeleteMapping("/{stageId}")
    public ResponseEntity<CommonResponseDto<Void>> deletedStage(@PathVariable Long stageId) {
        CommonResponseDto<Void> responseDto = stageService.deletedStage(stageId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
