package com.webflux.rr.flashcards.domain.service;

import com.webflux.rr.flashcards.domain.document.Card;
import com.webflux.rr.flashcards.domain.document.StudyDocument;
import com.webflux.rr.flashcards.domain.mapper.StudyDomainMapper;
import com.webflux.rr.flashcards.domain.repository.StudyRepository;
import com.webflux.rr.flashcards.domain.service.query.DeckQueryService;
import com.webflux.rr.flashcards.domain.service.query.UserQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public class StudyService {

    private final UserQueryService userQueryService;
    private final DeckQueryService deckQueryService;
    private final StudyRepository studyRepository;
    private final Logger log = LoggerFactory.getLogger(StudyService.class);

    public StudyService(UserQueryService userQueryService, DeckQueryService deckQueryService, StudyRepository studyRepository) {
        this.userQueryService = userQueryService;
        this.deckQueryService = deckQueryService;
        this.studyRepository = studyRepository;
    }

    public Mono<StudyDocument> start(final StudyDocument document) {
        return userQueryService.findById(document.userId())
                .flatMap(user -> deckQueryService.findById(document.studyDeck().deckId()))
                .flatMap(deck -> getCards(document, deck.cards()))
                .map(study -> {
                    study.addQuestion(StudyDomainMapper.generateRandomQuestion(study.studyDeck().cards()));
                    return study;
                })
                .doFirst(() -> log.info("==== generating a random question"))
                .flatMap(studyRepository::save)
                .doOnSuccess(study -> log.info("==== a follow study was save: {}", study));
    }

    private Mono<StudyDocument> getCards(final StudyDocument document, Set<Card> cards) {
        return Flux.fromIterable(cards)
                .doFirst(() -> log.info("==== copy cards to new study"))
                .map(StudyDomainMapper::cardtoStudyCard)
                .collectList()
                .map(studyCards -> document.studyDeck().toBuilder().cards(Set.copyOf(studyCards)).build())
                .map(studyDeck -> document.toBuilder().studyDeck(studyDeck).build());
    }
}
