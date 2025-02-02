package com.sparta.workflowhelper.domain.comment.entity;

import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.common.entity.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    // 정적 팩토리 메서드를 사용하도록 하기 위한 private 생성자
    private Comment(String content, User user, Card card) {
        this.content = content;
        this.user = user;
        this.card = card;
    }

    // 새로운 Comment 인스턴스를 생성하는 정적 팩토리 메서드
    public static Comment create(String content, User user, Card card) {
        return new Comment(content, user, card);
    }

    // 댓글의 content를 수정하는 정적 팩토리 메서드
    public void updateContent(String content) {
        this.content = content;
    }
}
