package com.webflux.rr.flashcards.domain.service;

import com.webflux.rr.flashcards.domain.document.DeckDocument;
import com.webflux.rr.flashcards.domain.repository.DeckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final Logger log = LoggerFactory.getLogger(DeckService.class);

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Mono<DeckDocument> save(final DeckDocument document) {
        return deckRepository.save(document)
                .doFirst(() -> log.info("==== try to save a follow deck {}", document));
    }
}
