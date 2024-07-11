package com.sparta.workflowhelper.domain.worker.adapter;

import com.sparta.workflowhelper.domain.worker.entity.Worker;
import com.sparta.workflowhelper.domain.worker.repository.WorkerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerAdapter {

    private final WorkerRepository workerRepository;

    public void saveAll(List<Worker> workers) {
        workerRepository.saveAll(workers);
    }

    public boolean existsById(Long userId) {
        return workerRepository.existsById(userId);
    }

}
