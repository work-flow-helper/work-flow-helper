package com.sparta.workflowhelper.domain.stage.repository;


import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findByProjectOrderByPositionAsc(Project project);

    // 지정된 프로젝트 내에서 시작 위치(startPosition)부터 끝 위치(endPosition)까지의 스테이지들의 position 값을 1씩 증가시킴
    @Transactional
    @Modifying
    @Query("UPDATE Stage s SET s.position = s.position + 1 WHERE s.project = :project AND s.position >= :startPosition AND s.position < :endPosition")
    void incrementPositions(@Param("project") Project project,
            @Param("startPosition") int startPosition, @Param("endPosition") int endPosition);

    // 지정된 프로젝트 내에서 시작 위치(startPosition)부터 끝 위치(endPosition)까지의 스테이지들의 position 값을 1씩 감소시킴
    @Transactional
    @Modifying
    @Query("UPDATE Stage s SET s.position = s.position - 1 WHERE s.project = :project AND s.position > :startPosition AND s.position <= :endPosition")
    void decrementPositions(@Param("project") Project project,
            @Param("startPosition") int startPosition, @Param("endPosition") int endPosition);

    List<Stage> findAllByProjectIdOrderByPositionAsc(Long projectId);
}
