package com.webflux.rr.flashcards.api.controller;

import com.webflux.rr.flashcards.api.controller.request.DeckRequest;
import com.webflux.rr.flashcards.api.controller.response.DeckResponse;
import com.webflux.rr.flashcards.api.mapper.DeckMapper;
import com.webflux.rr.flashcards.core.validation.MongoId;
import com.webflux.rr.flashcards.domain.service.DeckService;
import com.webflux.rr.flashcards.domain.service.query.DeckQueryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("decks")
public class DeckController {

    private final DeckService deckService;
    private final DeckQueryService deckQueryService;
    private final Logger log = LoggerFactory.getLogger(DeckService.class);

    public DeckController(DeckService deckService, DeckQueryService deckQueryService) {
        this.deckService = deckService;
        this.deckQueryService = deckQueryService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DeckResponse> save(@Valid @RequestBody DeckRequest deckRequest) {
        return deckService.save(DeckMapper.toDocument(deckRequest))
                .doFirst(() -> log.info("==== Saving a user with follow data {}", deckRequest))
                .map(DeckMapper::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<DeckResponse> findById(@PathVariable @Valid @MongoId(message = "deckController.id") String id) {
        return deckQueryService.findById(id)
                .doFirst(() -> log.info("==== Finding a deck with follow id {}", id))
                .map(DeckMapper::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Flux<DeckResponse> findAll() {
        return deckQueryService.findAll()
                .doFirst(() -> log.info("==== Find all Decks"))
                .map(DeckMapper::toResponse);
    }
}
