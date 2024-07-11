package com.sparta.workflowhelper.domain.card.repository;

import com.sparta.workflowhelper.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
