package com.sparta.workflowhelper.domain.comment.adapter;

import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.card.repository.CardRepository;
import com.sparta.workflowhelper.domain.comment.entity.Comment;
import com.sparta.workflowhelper.domain.comment.repository.CommentRepository;
import com.sparta.workflowhelper.global.exception.CardNotFoundException;
import com.sparta.workflowhelper.global.exception.customexceptions.CommentNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentAdapter {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentNotFoundException(NotFoundErrorCode.NOT_FOUND_COMMENT_ENTITY.getMessage()));
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
