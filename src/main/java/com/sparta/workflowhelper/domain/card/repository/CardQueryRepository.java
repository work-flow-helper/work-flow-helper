package com.sparta.workflowhelper.domain.card.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.workflowhelper.domain.card.dto.CardDetailResponseDto;
import com.sparta.workflowhelper.domain.card.entity.QCard;
import com.sparta.workflowhelper.domain.mapping.entity.QProjectMember;
import com.sparta.workflowhelper.domain.user.entity.QUser;
import com.sparta.workflowhelper.domain.worker.dto.WorkerInfoDto;
import com.sparta.workflowhelper.domain.worker.entity.QWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "CardQueryRepository 기능")
@Repository
@RequiredArgsConstructor
public class CardQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CardDetailResponseDto findCardDetailWithWorkers(Long cardId) {

        QCard card = QCard.card;
        QWorker worker = QWorker.worker;
        QProjectMember projectMember = QProjectMember.projectMember;
        QUser user = QUser.user;

        return queryFactory
                .select(Projections.constructor(
                        CardDetailResponseDto.class,
                        card.id,
                        card.title,
                        card.content,
                        card.deadline,
                        Projections.list(
                                Projections.constructor(
                                        WorkerInfoDto.class,
                                        user.id,
                                        user.nickname
                                )
                        )
                ))
                .from(card)
                .leftJoin(card.workers, worker)
                .leftJoin(worker.projectMember, projectMember)
                .leftJoin(projectMember.user, user)
                .where(card.id.eq(cardId))
                .fetchOne();
    }
}
