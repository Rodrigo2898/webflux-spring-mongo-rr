package com.webflux.rr.flashcards.domain.repository;

import com.webflux.rr.flashcards.domain.document.DeckDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DeckRepository extends ReactiveMongoRepository<DeckDocument, String> {
}
