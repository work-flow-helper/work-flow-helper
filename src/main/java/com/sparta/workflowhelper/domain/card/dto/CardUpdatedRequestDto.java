package com.sparta.workflowhelper.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CardUpdatedRequestDto {

    @NotBlank(message = "제목은 필수 입력값 입니다.")
    private String title;

    @Size(max = 50000, message = "글자수는 50000자까지 입니다.")
    private String content;

    private LocalDateTime deadline;

    private List<Long> userIdList;
}
