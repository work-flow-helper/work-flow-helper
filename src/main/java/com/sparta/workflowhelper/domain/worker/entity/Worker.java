package com.sparta.workflowhelper.domain.worker.entity;

import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.common.entity.TimeStamped;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "workers")
public class Worker extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder(access = AccessLevel.PRIVATE)
    private Worker(User user, Card card) {
        this.user = user;
        this.card = card;
    }

    public static Worker createdWorker(User user, Card card) {
        Worker worker = Worker.builder()
                .user(user)
                .card(card)
                .build();

        card.addWorker(worker);

        return worker;
    }
}
