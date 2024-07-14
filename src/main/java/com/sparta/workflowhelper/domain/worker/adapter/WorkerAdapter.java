package com.sparta.workflowhelper.domain.worker.adapter;

import com.sparta.workflowhelper.domain.worker.entity.Worker;
import com.sparta.workflowhelper.domain.worker.repository.WorkerRepository;
import com.sparta.workflowhelper.global.exception.customexceptions.WorkerNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
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

    public Worker findById(Long workerId) {
        return workerRepository.findById(workerId)
                .orElseThrow(() -> new WorkerNotFoundException(
                        NotFoundErrorCode.NOT_FOUND_WORKER_ENTITY.getMessage()));
    }
}
