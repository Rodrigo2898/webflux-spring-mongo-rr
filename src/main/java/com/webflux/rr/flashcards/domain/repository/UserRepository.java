package com.webflux.rr.flashcards.domain.repository;

import com.webflux.rr.flashcards.domain.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserDocument, String> {
}
