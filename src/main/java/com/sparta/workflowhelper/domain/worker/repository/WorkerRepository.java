package com.sparta.workflowhelper.domain.worker.repository;

import com.sparta.workflowhelper.domain.worker.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
