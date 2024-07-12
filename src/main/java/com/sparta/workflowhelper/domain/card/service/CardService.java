package com.sparta.workflowhelper.domain.card.service;

import com.sparta.workflowhelper.domain.card.adapter.CardAdapter;
import com.sparta.workflowhelper.domain.card.dto.CardDetailQueryDto;
import com.sparta.workflowhelper.domain.card.dto.CardDetailResponseDto;
import com.sparta.workflowhelper.domain.card.dto.CardRequestDto;
import com.sparta.workflowhelper.domain.card.dto.CardSimpleQueryDto;
import com.sparta.workflowhelper.domain.card.dto.CardSimpleResponseDto;
import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.card.repository.CardQueryRepository;
import com.sparta.workflowhelper.domain.mapping.adapter.ProjectMemberAdapter;
import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.domain.worker.adapter.WorkerAdapter;
import com.sparta.workflowhelper.domain.worker.dto.WorkQueryDto;
import com.sparta.workflowhelper.domain.worker.dto.WorkerInfoDto;
import com.sparta.workflowhelper.domain.worker.entity.Worker;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectMemberNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "CardService 기능")
@Service
@RequiredArgsConstructor
public class CardService {

    private static int count = 0;

    private final CardAdapter cardAdapter;
    private final CardQueryRepository cardQueryRepository;
    private final StageAdapter stageAdapter;
    private final WorkerAdapter workerAdapter;
    private final UserAdapter userAdapter;
    private final ProjectMemberAdapter projectMemberAdapter;

    @Transactional
    public CardSimpleResponseDto createdCard(CardRequestDto requestDto, User user) {

        Stage stage = stageAdapter.findById(requestDto.getStageId());

        List<Long> projectMemberUserIdList = projectMemberAdapter.findUserIdsByProjectId(
                stage.getProject().getId());

        Set<Long> projectMemberUserIdSet = new HashSet<>(projectMemberUserIdList);

        checkUserInProjectMembers(projectMemberUserIdSet, user.getId());

        Integer position = createdPositionNumber();

        Card card = Card.createdCard(requestDto.getTitle(), position, requestDto.getContent(),
                requestDto.getDeadline(), stage);

        Card savedCard = cardAdapter.save(card);

        List<WorkerInfoDto> workerInfoDtoList = new ArrayList<>();

        if (requestDto.getWorkerIdList().isEmpty()) {
            return CardSimpleResponseDto.of(
                    savedCard.getId(),
                    savedCard.getTitle(),
                    savedCard.getStage().getTitle(),
                    savedCard.getPosition(),
                    workerInfoDtoList);
        }

        List<Worker> workerList = new ArrayList<>();

        for (Long workerId : requestDto.getWorkerIdList()) {

            checkUserInProjectMembers(projectMemberUserIdSet, workerId);

            User workerUser = userAdapter.findById(workerId);

            Worker worker = Worker.createdWorker(workerUser, savedCard);

            workerList.add(worker);

            workerInfoDtoList.add(WorkerInfoDto.of(workerId, workerUser.getNickname()));
        }

        workerAdapter.saveAll(workerList);

        return CardSimpleResponseDto.of(
                savedCard.getId(),
                savedCard.getTitle(),
                savedCard.getStage().getTitle(),
                savedCard.getPosition(),
                workerInfoDtoList);
    }

    @Transactional(readOnly = true)
    public CardDetailResponseDto findCard(Long cardId) {

        CardDetailQueryDto cardQueryDto = cardQueryRepository.findCardDetail(cardId);

        List<WorkQueryDto> workQueryDto = cardQueryRepository.findWorkerInfo(cardId);

        return CardDetailResponseDto.of(
                cardQueryDto.getCardId(),
                cardQueryDto.getTitle(),
                cardQueryDto.getTitle(),
                cardQueryDto.getContent(),
                cardQueryDto.getDeadline(),
                cardQueryDto.getPosition(),
                workQueryDto
        );
    }

    @Transactional(readOnly = true)
    public List<CardSimpleQueryDto> findAllCardByProjectId(Long projectId) {
        return cardQueryRepository.findAllCardByProjectId(projectId);
    }

    private Integer createdPositionNumber() {

        Integer position = count;

        count++;

        return position;
    }

    private void checkUserInProjectMembers(Set<Long> projectMemberUserIdSet, Long userId) {
        if (!projectMemberUserIdSet.contains(userId)) {
            throw new ProjectMemberNotFoundException(
                    NotFoundErrorCode.NOT_FOUND_PROJECT_MEMBER_ENTITY.getMessage());
        }
    }
}
