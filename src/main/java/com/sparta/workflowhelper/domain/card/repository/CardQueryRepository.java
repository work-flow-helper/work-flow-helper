package com.sparta.workflowhelper.domain.card.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.workflowhelper.domain.card.dto.CardDetailQueryDto;
import com.sparta.workflowhelper.domain.card.dto.CardSimpleQueryDto;
import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.card.entity.QCard;
import com.sparta.workflowhelper.domain.mapping.dto.ProjectMemberIdDto;
import com.sparta.workflowhelper.domain.mapping.entity.QProjectMember;
import com.sparta.workflowhelper.domain.project.entity.QProject;
import com.sparta.workflowhelper.domain.stage.entity.QStage;
import com.sparta.workflowhelper.domain.user.entity.QUser;
import com.sparta.workflowhelper.domain.worker.dto.WorkQueryDto;
import com.sparta.workflowhelper.domain.worker.entity.QWorker;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "CardQueryRepository 기능")
@Repository
@RequiredArgsConstructor
public class CardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CardDetailQueryDto findCardDetail(Long cardId) {
        QCard card = QCard.card;
        QStage stage = QStage.stage;

        return queryFactory
                .select(Projections.constructor(
                                CardDetailQueryDto.class,
                                card.id,
                                card.title,
                                stage.title,
                                card.content,
                                card.deadline,
                                card.position
                        )
                )
                .from(card)
                .leftJoin(card.stage, stage)
                .where(card.id.eq(cardId))
                .fetchOne();
    }

    public List<CardSimpleQueryDto> findAllCardByProjectId(Long projectId) {

        QCard card = QCard.card;
        QStage stage = QStage.stage;
        QProject project = QProject.project;

        return queryFactory
                .select(Projections.constructor(
                                CardSimpleQueryDto.class,
                                card.id,
                                card.title,
                                stage.title,
                                card.position
                        )
                )
                .from(card)
                .leftJoin(card.stage, stage)
                .leftJoin(stage.project, project)
                .where(card.stage.project.id.eq(projectId))
                .fetch();
    }

    public List<Card> findAllCardByStageId(Long stageId) {
        QCard card = QCard.card;
        QStage stage = QStage.stage;

        return queryFactory
                .selectFrom(card)
                .leftJoin(card.stage, stage)
                .where(card.stage.id.eq(stageId))
                .fetch();
    }

    public List<WorkQueryDto> findWorkerInfo(Long cardId) {

        QWorker worker = QWorker.worker;
        QUser user = QUser.user;
        QCard card = QCard.card;

        return queryFactory
                .select(Projections.constructor(
                                WorkQueryDto.class,
                                user.id,
                                user.nickname
                        )
                )
                .from(card)
                .leftJoin(card.workers, worker)
                .leftJoin(worker.user, user)
                .where(card.id.eq(cardId))
                .fetch();
    }


    public List<ProjectMemberIdDto> findProjectMemberIdListByCardId(Long cardId) {
        QCard card = QCard.card;
        QStage stage = QStage.stage;
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        QUser user = QUser.user;

        return queryFactory
                .select(
                        Projections.constructor(
                                ProjectMemberIdDto.class,
                                user.id
                        )
                )
                .from(card)
                .leftJoin(card.stage, stage)
                .leftJoin(stage.project, project)
                .leftJoin(project.projectMembers, projectMember)
                .leftJoin(projectMember.user, user)
                .where(card.id.eq(cardId))
                .fetch();
    }

    public Integer findLastPositionInStage(Long stageId) {

        QStage stage = QStage.stage;
        QCard card = QCard.card;

        return queryFactory
                .select(card.position.max())
                .from(card)
                .leftJoin(card.stage, stage)
                .where(card.stage.id.eq(stageId))
                .fetchOne();
    }
}
