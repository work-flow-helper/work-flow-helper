package com.sparta.workflowhelper.domain.card.service;

import com.sparta.workflowhelper.domain.card.adapter.CardAdapter;
import com.sparta.workflowhelper.domain.card.dto.CardDetailQueryDto;
import com.sparta.workflowhelper.domain.card.dto.CardDetailResponseDto;
import com.sparta.workflowhelper.domain.card.dto.CardPositionRequestDto;
import com.sparta.workflowhelper.domain.card.dto.CardRequestDto;
import com.sparta.workflowhelper.domain.card.dto.CardSimpleQueryDto;
import com.sparta.workflowhelper.domain.card.dto.CardSimpleResponseDto;
import com.sparta.workflowhelper.domain.card.dto.CardUpdatedRequestDto;
import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.card.repository.CardQueryRepository;
import com.sparta.workflowhelper.domain.mapping.dto.ProjectMemberIdDto;
import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.stage.repository.StageQueryRepository;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.domain.worker.dto.WorkQueryDto;
import com.sparta.workflowhelper.domain.worker.dto.WorkerInfoDto;
import com.sparta.workflowhelper.domain.worker.entity.Worker;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectMemberNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "CardService 기능")
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardAdapter cardAdapter;
    private final CardQueryRepository cardQueryRepository;
    private final StageAdapter stageAdapter;
    private final UserAdapter userAdapter;
    private final StageQueryRepository stageQueryRepository;

    /**
     * 카드 생성 로직
     *
     * @param requestDto 카드 상세 정보
     * @param user       카드 등록자
     * @return 카드의 간단한 정보
     */
    @Transactional
    public CardSimpleResponseDto createdCard(CardRequestDto requestDto, User user) {

        Stage stage = stageAdapter.findById(requestDto.getStageId());

        List<ProjectMemberIdDto> projectMemberUserIdList =
                stageQueryRepository.findProjectMemberIdListByStageId(stage.getId());

        Set<Long> projectMemberUserIdSet = convertToProjectMemberUserIdSet(projectMemberUserIdList);

        checkUserIsProjectMembers(projectMemberUserIdSet, user.getId());

        Integer position = createdPositionNumber(stage);

        Card card = Card.createdCard(requestDto.getTitle(), position, requestDto.getContent(),
                requestDto.getDeadline(), stage);

        Card savedCard = cardAdapter.save(card);

        List<WorkerInfoDto> workerInfoDtoList = new ArrayList<>();

        if (requestDto.getUserIdList() == null) {
            return CardSimpleResponseDto.of(savedCard.getId(), savedCard.getTitle(),
                    savedCard.getStage().getTitle(), savedCard.getPosition(), workerInfoDtoList);
        }

        Set<Long> inputUserIdSet = new HashSet<>(requestDto.getUserIdList());

        List<Worker> workerList = createdAndSaveWorkerAndGetDtoList(inputUserIdSet, card);

        workerList.forEach(worker -> workerInfoDtoList.add(
                WorkerInfoDto.of(worker.getUser().getId(), worker.getUser().getNickname())
        ));

        return CardSimpleResponseDto.of(savedCard.getId(), savedCard.getTitle(),
                savedCard.getStage().getTitle(), savedCard.getPosition(), workerInfoDtoList);
    }

    /**
     * 카드 단일 조회
     *
     * @param cardId 조회할 카드의 고유번호
     * @return 조회한 카드의 상세 정보
     */
    @Transactional(readOnly = true)
    public CardDetailResponseDto<WorkQueryDto> findCard(Long cardId) {

        CardDetailQueryDto cardQueryDto = cardQueryRepository.findCardDetail(cardId);

        List<WorkQueryDto> workQueryDto = cardQueryRepository.findWorkerInfo(cardId);

        return CardDetailResponseDto.of(cardQueryDto.getCardId(), cardQueryDto.getTitle(),
                cardQueryDto.getTitle(), cardQueryDto.getContent(), cardQueryDto.getDeadline(),
                cardQueryDto.getPosition(), workQueryDto
        );
    }

    /**
     * 프로젝트에 속한 카드 전체 조회
     *
     * @param projectId 조회할 프로젝트의 고유번호
     * @return 프로젝트내의 모든 카드들의 간단한 정보
     */
    @Transactional(readOnly = true)
    public List<CardSimpleQueryDto> findAllCardByProjectId(Long projectId) {
        return cardQueryRepository.findAllCardByProjectId(projectId);
    }

    /**
     * 카드 수정 기능
     *
     * @param cardId     수정할 카드의 고유 번호
     * @param requestDto 수정할 카드의 내용들
     * @param user       수정을 진행할 수정자 (프로젝트에 참가한 맴버만 가능)
     * @return 수정한 카드의 상세 정보
     */
    @Transactional
    public CardDetailResponseDto<WorkerInfoDto> updatedCard(Long cardId,
            CardUpdatedRequestDto requestDto, User user) {

        checkUserIsProjectMembers(cardId, user);

        Card card = cardAdapter.findById(cardId);

        card.updatedCard(requestDto.getTitle(), requestDto.getContent(), requestDto.getDeadline());

        Set<Worker> workerSet = card.getWorkers();

        Set<Long> WorkerUserIdSet = workerSet.stream()
                .map(worker -> worker.getUser().getId())
                .collect(Collectors.toSet());

        Set<Long> inputUserIdSet = new HashSet<>(requestDto.getUserIdList());

        workerSet.removeIf(worker -> !inputUserIdSet.contains(worker.getUser().getId()));

        inputUserIdSet.removeAll(WorkerUserIdSet);

        List<Worker> workerList = createdAndSaveWorkerAndGetDtoList(inputUserIdSet, card);

        workerSet.addAll(workerList);

        List<WorkerInfoDto> workerInfoDtoList = new ArrayList<>();

        workerSet.forEach(worker -> workerInfoDtoList.add(
                WorkerInfoDto.of(worker.getUser().getId(), worker.getUser().getNickname())
        ));

        return CardDetailResponseDto.of(card.getId(), card.getTitle(), card.getTitle(),
                card.getContent(), card.getDeadline(), card.getPosition(), workerInfoDtoList
        );
    }

    /**
     * 카드 삭제 기능
     *
     * @param cardId 삭제할 카드의 고유번호
     * @param user   삭제를 진행할 유저
     */
    @Transactional
    public void deletedCard(Long cardId, User user) {

        checkUserIsProjectMembers(cardId, user);

        Card card = cardAdapter.findById(cardId);

        cardAdapter.delete(card);
    }

    /**
     * 카드의 포지션을 변경하는 로직
     *
     * @param cardId     변경할 카드의 고유번호
     * @param requestDto 어디로 변경을 진행할지 확인할 데이터
     * @param user       변경을 진행할 유저
     * @return 변경후 간단한 카드 정보 리턴
     */
    @Transactional
    public CardSimpleResponseDto changeCardPosition(Long cardId, CardPositionRequestDto requestDto,
            User user) {

        checkUserIsProjectMembers(cardId, user);

        Card moveCard = cardAdapter.findById(cardId);

        Stage stage;

        if (Objects.equals(moveCard.getStage().getId(), requestDto.getStageId())) {
            stage = moveCard.getStage();
        } else {
            stage = stageAdapter.findById(requestDto.getStageId());
        }

        List<Card> cardList = cardQueryRepository.findAllCardByStageId(stage.getId());

        Integer newPosition = requestDto.getNewPosition();

        Integer oldPosition = moveCard.getPosition();

        if (newPosition > oldPosition) {
            for (Card card : cardList) {
                if (card.getPosition() > oldPosition && card.getPosition() <= newPosition) {
                    card.updatePositionNumber(card.getPosition() - 1);
                }
            }
        } else if (newPosition < oldPosition) {
            for (Card card : cardList) {
                if (card.getPosition() >= newPosition && card.getPosition() < oldPosition) {
                    card.updatePositionNumber(card.getPosition() + 1);
                }
            }
        }

        moveCard.updatePosition(newPosition, stage);

        return CardSimpleResponseDto.of(moveCard.getId(), moveCard.getTitle(),
                moveCard.getStage().getTitle(), moveCard.getPosition()
        );
    }

    /**
     * 작업자 생성후 저장하고 작업자를 리턴
     *
     * @param inputUserIdSet 작업자 추가를 진행할 유저의 고유번호 Set
     * @param card           작업자가 배정될 카드
     * @return 작업자 엔티티
     */
    private List<Worker> createdAndSaveWorkerAndGetDtoList(Set<Long> inputUserIdSet,
            Card card) {

        List<Worker> workerList = new ArrayList<>();

        for (Long inputUserId : inputUserIdSet) {

            User workerUser = userAdapter.findById(inputUserId);

            Worker worker = Worker.createdWorker(workerUser, card);

            worker.addWorkerInCard(card);

            workerList.add(worker);
        }

        return workerList;
    }

    /**
     * 카드를 만들때 카드의 포지션을 해당 스테이지의 맨 마지막 번호로 만들어주는 메서드
     *
     * @return 포지션 번호
     */
    private Integer createdPositionNumber(Stage stage) {

        Integer lastCardPosition = cardQueryRepository.findLastPositionInStage(stage.getId());

        return lastCardPosition + 1;
    }

    /**
     * 카드 수정, 삭제 권한에 접근하려는 로그인 유저가 프로젝트의 맴버인지 확인
     *
     * @param cardId 접근하려고 하는 카드의 고유번호
     * @param user   로그인한 유저
     */
    private void checkUserIsProjectMembers(Long cardId, User user) {

        List<ProjectMemberIdDto> projectMemberUserIdList =
                cardQueryRepository.findProjectMemberIdListByCardId(cardId);

        Set<Long> projectMemberUserIdSet = convertToProjectMemberUserIdSet(projectMemberUserIdList);

        checkUserIsProjectMembers(projectMemberUserIdSet, user.getId());
    }

    /**
     * 작성, 수정, 삭제를 진행하는 유저가 프로젝트 맴버에 속하는지 확인
     *
     * @param projectMemberUserIdSet 프로젝트에 속한 유저의 고유번호 Set
     * @param userId                 작성, 수정, 삭제에 접근할 유저의 고유번호
     */
    private void checkUserIsProjectMembers(Set<Long> projectMemberUserIdSet, Long userId) {

        if (!projectMemberUserIdSet.contains(userId)) {
            throw new ProjectMemberNotFoundException(
                    NotFoundErrorCode.NOT_FOUND_PROJECT_MEMBER_ENTITY.getMessage());
        }
    }

    /**
     * 프로젝트에 속한 맴버의 고유번호 Id List -> Set 으로 변환
     *
     * @param projectMemberUserIdList QueryDSL 로 찾아온 프로젝트에 속한 맴버들의 id 리스트
     * @return 맴버들의 id를 모아둔 Set
     */
    private Set<Long> convertToProjectMemberUserIdSet(
            List<ProjectMemberIdDto> projectMemberUserIdList) {

        Set<Long> projectMemberUserIdSet = new HashSet<>();

        for (ProjectMemberIdDto projectMemberIdDto : projectMemberUserIdList) {
            projectMemberUserIdSet.add(projectMemberIdDto.getUserId());
        }

        return projectMemberUserIdSet;
    }
}
