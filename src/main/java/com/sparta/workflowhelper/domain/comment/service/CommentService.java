package com.sparta.workflowhelper.domain.comment.service;

import com.sparta.workflowhelper.domain.card.adapter.CardAdapter;
import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.card.repository.CardQueryRepository;
import com.sparta.workflowhelper.domain.comment.adapter.CommentAdapter;
import com.sparta.workflowhelper.domain.comment.dto.CommentRequestDto;
import com.sparta.workflowhelper.domain.comment.dto.CommentResponseDto;
import com.sparta.workflowhelper.domain.comment.entity.Comment;
import com.sparta.workflowhelper.domain.mapping.dto.ProjectMemberIdDto;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.global.common.dto.CommonResponseDto;
import com.sparta.workflowhelper.global.exception.customexceptions.ProjectMemberNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.HashSet;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentAdapter commentAdapter;

    private final CardAdapter cardAdapter;

    private final CardQueryRepository cardQueryRepository;

    // - 완료
    @Transactional
    public CommonResponseDto<CommentResponseDto> createdComment(CommentRequestDto requestDto, User user) {

        checkUserIsProjectMembers(requestDto.getCardId(), user);

        Card card = cardAdapter.findById(requestDto.getCardId());

        Comment comment = Comment.create(requestDto.getContent(), user, card);
        Comment savedComment = commentAdapter.save(comment);

        CommentResponseDto responseDto = CommentResponseDto.from(savedComment);
        return CommonResponseDto.of(201, "댓글 등록", responseDto);
    }

    //  - 완료
    @Transactional(readOnly = true)
    public CommonResponseDto<List<CommentResponseDto>> getComment() {
        List<Comment> comments = commentAdapter.findAll();
        List<CommentResponseDto> responseDtos = comments.stream()
            .map(CommentResponseDto::from)
            .toList();

        return CommonResponseDto.of(200, "댓글 전체 조회", responseDtos);
    }

    @Transactional
    public CommonResponseDto<CommentResponseDto> updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentAdapter.findById(commentId);

        checkUserIsProjectMembers(requestDto.getCardId(), user);

        comment.updateContent(requestDto.getContent());

        Comment updatedComment = commentAdapter.save(comment);

        CommentResponseDto responseDto = CommentResponseDto.from(updatedComment);
        return CommonResponseDto.of(200, "댓글 수정", responseDto);
    }

    @Transactional
    public CommonResponseDto<Void> deleteComment(Long commentId, User user) {
        Comment comment = commentAdapter.findById(commentId);

        checkUserIsProjectMembers(comment.getCard().getId(), user);

        commentAdapter.delete(comment);

        return CommonResponseDto.of(204, "댓글 삭제 완료");
    }

    private void checkUserIsProjectMembers(Long cardId, User user) {
        List<ProjectMemberIdDto> projectMembersIdList = cardQueryRepository.findProjectMemberIdListByCardId(cardId);

        Set<Long> projectMembersIdSet = new HashSet<>();

        for (ProjectMemberIdDto idDto : projectMembersIdList) {
            projectMembersIdSet.add(idDto.getUserId());
        }

        if (!projectMembersIdSet.contains(user.getId())) {
            throw new ProjectMemberNotFoundException(
                NotFoundErrorCode.NOT_FOUND_PROJECT_MEMBER_ENTITY.getMessage());
        }
    }
}
