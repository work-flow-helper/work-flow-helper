package com.sparta.workflowhelper.domain.stage.adapter;

import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.stage.repository.StageRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.StageNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageAdapter {

    private final StageRepository stageRepository;

    public Stage findById(Long stageId) {
        return stageRepository.findById(stageId)
                .orElseThrow(() -> new StageNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_STAGE_ENTITY.getMessage()));
    }
}
