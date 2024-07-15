package com.sparta.workflowhelper.domain.project.entity;


import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import com.sparta.workflowhelper.global.common.entity.TimeStamped;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "projects")
public class Project extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // Board Title

    @Column
    private String info; // Board simple description

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Project(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public static Project createdProject(String title, String info) {
        return Project.builder()
                .title(title)
                .info(info)
                .build();
        //  ProjectMember projectMember = new ProjectMember(project, user);
        //  project.projectMembers.add(ProjectMember.of(user, project));
    }

    public void addProjectMember(ProjectMember projectMember) {
        this.projectMembers.add(projectMember);
    }

    public void changeOf(String title, String info) {
        this.title = title;
        this.info = info;

    }
}
