package com.sparta.workflowhelper.domain.stage.repository;


import com.sparta.workflowhelper.domain.project.entity.Project;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {

    List<Stage> findByProjectOrderByPositionAsc(Project project);

}
