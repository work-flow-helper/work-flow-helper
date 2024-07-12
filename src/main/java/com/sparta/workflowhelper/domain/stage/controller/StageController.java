package com.sparta.workflowhelper.domain.stage.controller;

import com.sparta.workflowhelper.domain.stage.dto.StageRequestDto;
import com.sparta.workflowhelper.domain.stage.dto.StageResponseDto;
import com.sparta.workflowhelper.domain.stage.service.StageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/columns")
public class StageController {

    private  final StageService stageService;

    @PostMapping
    public ResponseEntity<StageResponseDto> createdStage(@RequestBody StageRequestDto requestDto) {
        StageResponseDto responseDto = stageService.createdStage(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
