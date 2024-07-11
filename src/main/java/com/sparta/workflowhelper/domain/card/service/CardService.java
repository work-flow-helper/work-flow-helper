package com.sparta.workflowhelper.domain.card.service;

import com.sparta.workflowhelper.domain.card.adapter.CardAdapter;
import com.sparta.workflowhelper.domain.card.dto.CardRequestDto;
import com.sparta.workflowhelper.domain.card.dto.CardSimpleResponseDto;
import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.mapping.adapter.ProjectMemberAdapter;
import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.domain.worker.adapter.WorkerAdapter;
import com.sparta.workflowhelper.domain.worker.dto.WorkerInfoDto;
import com.sparta.workflowhelper.domain.worker.entity.Worker;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectMemberNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private static int count = 0;

    private final CardAdapter cardAdapter;
    private final StageAdapter stageAdapter;
    private final WorkerAdapter workerAdapter;
    private final ProjectMemberAdapter projectMemberAdapter;

    @Transactional
    public CardSimpleResponseDto createdCard(CardRequestDto requestDto,
            User user) {

        Stage stage = stageAdapter.findById(requestDto.getStageId());

        if (!projectMemberAdapter.existsByIdAndProjectId(user.getId(),
                stage.getProject().getId())) {
            throw new ProjectMemberNotFoundException(
                    NotFoundErrorCode.NOT_FOUND_PROJECT_MEMBER_ENTITY.getMessage());
        }

        Integer position = createdPositionNumber();

        Card card = Card.createdCard(requestDto.getTitle(), position, requestDto.getContent(),
                requestDto.getDeadline(), stage);

        Card savedCard = cardAdapter.save(card);

        List<Worker> workerList = new ArrayList<>();
        List<WorkerInfoDto> workerInfoDtoList = new ArrayList<>();

        for (Long workerId : requestDto.getWorkerIdList()) {
            ProjectMember member = projectMemberAdapter.findByIdAndProjectId(workerId,
                    stage.getProject().getId());
            Worker worker = Worker.createdWorker(member, savedCard);
            workerList.add(worker);
            workerInfoDtoList.add(
                    WorkerInfoDto.of(workerId, worker.getProjectMember().getUser().getNickname()));
        }

        workerAdapter.saveAll(workerList);

        return CardSimpleResponseDto.of(savedCard.getId(), savedCard.getTitle(),
                savedCard.getPosition(), workerInfoDtoList);
    }

    private Integer createdPositionNumber() {
        Integer position = count * 100;

        count++;

        return position;
    }
}
