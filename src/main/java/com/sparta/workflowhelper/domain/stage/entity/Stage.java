package com.sparta.workflowhelper.domain.stage.entity;

import com.sparta.workflowhelper.domain.project.entity.Project;
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
@Table(name = "stages")
public class Stage extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // 정적 팩토리 메서드를 사용하도록 하기 위한 private 생성자
    private Stage(String title, Integer position, Project project) {
        this.title = title;
        this.position = position;
        this.project = project;
    }

    // 새로운 Stage 인스턴스를 생성하는 정적 팩토리 메서드
    public static Stage createdStage(String title, Integer position, Project project) {
        return new Stage(title, position, project);
    }

    // Stage의 제목을 수정하는 메서드
    public void updateStage(String title) {
        this.title = title;
    }

    // Stage의 위치를 수정하는 메서드
    public void updatePosition(Integer position) {
        this.position = position;
    }
}
