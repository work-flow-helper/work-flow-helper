package com.sparta.workflowhelper.domain.stage.repository;


import com.sparta.workflowhelper.domain.stage.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {

}
