package com.sparta.workflowhelper.domain.card.controller;

import com.sparta.workflowhelper.domain.card.dto.CardRequestDto;
import com.sparta.workflowhelper.domain.card.dto.CardSimpleResponseDto;
import com.sparta.workflowhelper.domain.card.service.CardService;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import com.sparta.workflowhelper.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;

    @PostMapping("/cards")
    public ResponseEntity<CommonResponseDto<CardSimpleResponseDto>> createdCard(
            @Valid @RequestBody CardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CardSimpleResponseDto responseDto = cardService.createdCard(requestDto,
                userDetails.getUser());
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.of(HttpStatus.CREATED.value(), "카드 등록 성공", responseDto));
    }
}
