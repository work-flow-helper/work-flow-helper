package com.sparta.workflowhelper.domain.stage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StagePositionRequestDto {

    @NotNull(message = "Position은 항목입니다.")
    @Min(value = 1, message = "Position은 1 이상이어야 합니다.")
    private int position;

}
