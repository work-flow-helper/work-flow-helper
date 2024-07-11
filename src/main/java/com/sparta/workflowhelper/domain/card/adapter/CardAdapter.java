package com.sparta.workflowhelper.domain.card.adapter;

import com.sparta.workflowhelper.domain.card.entity.Card;
import com.sparta.workflowhelper.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardAdapter {

    private final CardRepository cardRepository;

    public Card save(Card card) {
        return cardRepository.save(card);
    }
}
