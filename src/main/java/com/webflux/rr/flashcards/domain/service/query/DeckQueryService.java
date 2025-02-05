package com.webflux.rr.flashcards.domain.service.query;

import com.webflux.rr.flashcards.domain.document.DeckDocument;
import com.webflux.rr.flashcards.domain.exception.NotFoundException;
import com.webflux.rr.flashcards.domain.repository.DeckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.webflux.rr.flashcards.domain.exception.BaseErrorMessage.DECK_NOT_FOUND;

@Service
public class DeckQueryService {

    private final DeckRepository deckRepository;
    private final Logger log = LoggerFactory.getLogger(DeckQueryService.class);

    public DeckQueryService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Mono<DeckDocument> findById(final String id) {
        return deckRepository.findById(id)
                .doFirst(() -> log.info("==== try to find deck with id {} ",id))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(
                        () -> Mono.error(new NotFoundException(DECK_NOT_FOUND.params(id).getMessage()))
                ));
    }

    public Flux<DeckDocument> findAll() {
        return deckRepository.findAll()
                .doFirst(() -> log.info("==== try to find all decks "));
    }
}
