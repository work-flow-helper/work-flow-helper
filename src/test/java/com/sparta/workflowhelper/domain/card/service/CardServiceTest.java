package com.sparta.workflowhelper.domain.card.service;

import com.sparta.workflowhelper.domain.card.adapter.CardAdapter;
import com.sparta.workflowhelper.domain.card.repository.CardQueryRepository;
import com.sparta.workflowhelper.domain.project.adapter.ProjectAdapter;
import com.sparta.workflowhelper.domain.stage.adapter.StageAdapter;
import com.sparta.workflowhelper.domain.stage.repository.StageQueryRepository;
import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardServiceTest {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardAdapter cardAdapter;

    @Autowired
    private CardQueryRepository cardQueryRepository;

    @Autowired
    private StageAdapter stageAdapter;

    @Autowired
    private UserAdapter userAdapter;

    @Autowired
    private StageQueryRepository stageQueryRepository;

    @Autowired
    private ProjectAdapter projectAdapter;

//    @Test
//    @DisplayName("Init Card Data")
//    void init_Card_Data() {
    // 테스트시 아래를 주석 해제하고 테스트하세요~
//        Project project = Project.createdProject("Project title", "info");
//
//        projectAdapter.projectSave(project);
//
//        Stage stage = Stage.createdStage("1 Stage", 1, project);
//
//        stageAdapter.save(stage);
//
//        Stage stage2 = Stage.createdStage("2 Stage", 2, project);
//
//        stageAdapter.save(stage2);

//        Stage stage = stageAdapter.findById(4L);

//        Stage stage2 = stageAdapter.findById(5L);
//
//        int cardNumber = 0;
//        Integer positionA = 0;
//        Integer positionB = 0;
//
//        for (int i = 0; i < 5; i++) {
//            if (i % 2 == 0) {
//                Card card = Card.createdCard(
//                        "card title" + cardNumber,
//                        positionA,
//                        "content" + cardNumber,
//                        LocalDateTime.now(),
//                        stage
//                );
//
//                positionA++;
//
//                cardAdapter.save(card);
//            } else {
//            Card card = Card.createdCard(
//                    "card title" + cardNumber,
//                    positionB,
//                    "content" + cardNumber,
//                    LocalDateTime.now(),
//                    stage2
//            );
//
//            positionB++;
//
//            cardAdapter.save(card);
//            }
//        }
//    }
}