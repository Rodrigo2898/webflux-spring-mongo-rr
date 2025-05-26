package com.webflux.rr.flashcards.domain.mapper;

import com.webflux.rr.flashcards.domain.document.Card;
import com.webflux.rr.flashcards.domain.document.Question;
import com.webflux.rr.flashcards.domain.document.StudyCard;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class StudyDomainMapper {

    public static Set<StudyCard> toStudyCard(final Set<Card> cards) {
        return cards.stream()
                .map(StudyDomainMapper::cardtoStudyCard)
                .collect(Collectors.toSet());
    }

    public static StudyCard cardtoStudyCard(final Card card) {
        return StudyCard.builder()
                .front(card.front())
                .back(card.back())
                .build();
    }

    public static Question generateRandomQuestion(final Set<StudyCard> cards) {
        var values = new ArrayList<>(cards);
        var random = new Random();
        var position = random.nextInt(values.size());
        return toQuestion(values.get(position));
    }

    public static Question toQuestion(final StudyCard card) {
        return Question.builder()
                .answered(card.front())
                .answeredIn(OffsetDateTime.now())
                .expected(card.back())
                .build();
    }
}
