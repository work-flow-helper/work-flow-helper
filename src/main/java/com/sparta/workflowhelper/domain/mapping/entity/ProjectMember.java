package com.sparta.workflowhelper.domain.mapping.entity;

import com.sparta.workflowhelper.domain.project.entity.Project;
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
@Table(name = "project_members")
public class ProjectMember extends TimeStamped {
    // 수정 사항 : 타임스탬프 상속받음
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public ProjectMember(Project project, User user) {
        this.project = project;
        this.user = user;
    }

    @Builder(access = AccessLevel.PRIVATE)
    private ProjectMember(User user, Project project) {
        this.user = user;
        this.project = project;
    }

    public static ProjectMember of(User user, Project project) {
        ProjectMember projectMember = ProjectMember.builder()
                .user(user)
                .project(project)
                .build();
        project.addProjectMember(projectMember);
        return projectMember;
    }
//
}
