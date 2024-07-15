package com.sparta.workflowhelper.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CardRequestDto {

    @NotNull(message = "스테이지 고유번호는 필수 입력값 입니다.")
    private Long stageId;

    @NotBlank(message = "제목은 필수 입력값 입니다.")
    @Size(max = 20, message = "카드제목은 20자까지 입력 가능합니다.")
    private String title;

    @Size(max = 50000, message = "글자수는 50000자까지 입니다.")
    private String content;

    private LocalDateTime deadline;

    private List<Long> userIdList;
}
