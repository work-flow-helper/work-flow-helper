package com.sparta.workflowhelper.domain.card.service;

import com.sparta.workflowhelper.domain.card.adapter.CardAdapter;
import com.sparta.workflowhelper.domain.card.dto.CardRequestDto;
import com.sparta.workflowhelper.domain.card.dto.CardSimpleResponseDto;
import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.entity.Stage;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import com.sparta.workflowhelper.domain.user.entity.User;
import com.sparta.workflowhelper.domain.worker.adapter.WorkerAdapter;
import com.sparta.workflowhelper.domain.worker.dto.WorkerInfoDto;
import com.sparta.workflowhelper.domain.worker.entity.Worker;
import com.sparta.workflowhelper.global.exception.customexceptions.UserNotFoundException;
import com.sparta.workflowhelper.global.exception.errorcodes.NotFoundErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardAdapter cardAdapter;
    private final StageAdapter stageAdapter;
    private final WorkerAdapter workerAdapter;
    private final UserAdapter userAdapter;

    @Transactional
    public CardSimpleResponseDto createdCard(CardRequestDto requestDto,
            User user) {

        // 프로젝트에 합류한 팀원인지 체크 해야하는데 어떻게 할까
        if (!workerAdapter.existsById(user.getId())) {
            throw new UserNotFoundException(NotFoundErrorCode.NOT_FOUND_USER_ENTITY.getMessage());
        }

        Stage stage = stageAdapter.findById(requestDto.getStageId());

        Card card = Card.createdCard(requestDto.getTitle(), 1, requestDto.getContent(),
                requestDto.getDeadline(), stage);

        Card savedCard = cardAdapter.save(card);

        List<Worker> workers = new ArrayList<>();
        List<WorkerInfoDto> workerInfoDtoList = new ArrayList<>();

        for (Long workerId : requestDto.getWorkerIdList()) {
            User workerUser = userAdapter.findById(workerId);
            Worker worker = Worker.createdWorker(workerUser, savedCard);
            workers.add(worker);
            workerInfoDtoList.add(WorkerInfoDto.of(workerId, worker.getUser().getNickname()));
        }

        workerAdapter.saveAll(workers);

        return CardSimpleResponseDto.of(savedCard.getId(), savedCard.getTitle(),
                savedCard.getPosition(), workerInfoDtoList);
    }
}
