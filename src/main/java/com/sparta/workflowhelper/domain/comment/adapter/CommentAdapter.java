package com.sparta.workflowhelper.domain.comment.adapter;

import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.card.repository.CardRepository;
import com.sparta.workflowhelper.domain.comment.entity.Comment;
import com.sparta.workflowhelper.domain.comment.repository.CommentRepository;
import com.sparta.workflowhelper.global.exception.CardNotFoundException;
import com.sparta.workflowhelper.global.exception.customexceptions.CommentNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAdapter {

    private final CommentRepository commentRepository;

    private final CardRepository cardRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Card findById(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new CardNotFoundException(NotFoundErrorCode.NOT_FOUND_CARD_ENTITY.getMessage()));
    }
}
