package com.webflux.rr.flashcards.domain.service;

import com.webflux.rr.flashcards.domain.document.UserDocument;
import com.webflux.rr.flashcards.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserDocument> save(final UserDocument userDocument) {
        return userRepository.save(userDocument)
                .doFirst(() -> log.info("==== try to save a follow document: {}", userDocument));
    }
}
