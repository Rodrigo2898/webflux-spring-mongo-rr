package com.webflux.rr.flashcards.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StudyRepository extends ReactiveMongoRepository<StudyRepository, String> {
}
