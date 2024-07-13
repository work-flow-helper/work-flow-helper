package com.sparta.workflowhelper.domain.mapping.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.workflowhelper.domain.mapping.dto.ProjectMemberIdDto;
import com.sparta.workflowhelper.domain.mapping.entity.QProjectMember;
import com.sparta.workflowhelper.domain.project.entity.QProject;
import com.sparta.workflowhelper.domain.user.entity.QUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectQueryRepository {

    private final JPAQueryFactory queryFactory;


    public List<ProjectMemberIdDto> findUserIdsByProjectId(Long projectId) {

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
                .from(project)
                .leftJoin(project.projectMembers, projectMember)
                .leftJoin(projectMember.user, user)
                .where(project.id.eq(projectId))
                .fetch();
    }
}
