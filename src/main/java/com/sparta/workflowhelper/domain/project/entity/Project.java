package com.sparta.workflowhelper.domain.project.entity;


import com.sparta.workflowhelper.domain.mapping.entity.UserBoardMapping;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "projects")
public class Project extends TimeStamped {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String title; // Board Title

    @Column
    private String info; // Board simple description

    @Column
    private LocalDateTime deletedAt; // Soft delete Column

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_board_mapping_id")
    private UserBoardMapping userBoardMapping;


    public void deleteProject (){ // Soft delete Project
        this.deletedAt = LocalDateTime.now();
    }

}
