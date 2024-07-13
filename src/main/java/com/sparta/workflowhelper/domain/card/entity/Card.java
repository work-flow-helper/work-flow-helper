package com.sparta.workflowhelper.domain.card.entity;

import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.worker.entity.Worker;
import com.sparta.workflowhelper.global.common.entity.TimeStamped;
import jakarta.persistence.CascadeType;
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
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cards")
public class Card extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer position;

    private String content;

    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Worker> workers = new HashSet<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Card(String title, Integer position, String content, LocalDateTime deadline,
            Stage stage) {
        this.title = title;
        this.position = position;
        this.content = content;
        this.deadline = deadline;
        this.stage = stage;
    }

    public static Card createdCard(String title, Integer position, String content,
            LocalDateTime deadline, Stage stage) {
        return Card.builder()
                .title(title)
                .position(position)
                .content(content)
                .deadline(deadline)
                .stage(stage)
                .build();
    }

    public void updatedCard(String title, String content, LocalDateTime deadline) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
    }

    public void addWorker(Worker worker) {
        this.workers.add(worker);
    }

    public void updatePosition(Integer position) {
        this.position = position;
    }
}
