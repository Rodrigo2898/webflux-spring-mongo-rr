package com.webflux.rr.flashcards.domain.service;

import com.webflux.rr.flashcards.domain.document.UserDocument;
import com.webflux.rr.flashcards.domain.repository.UserRepository;
import com.webflux.rr.flashcards.domain.service.query.UserQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserQueryService userQueryService;
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, UserQueryService userQueryService) {
        this.userRepository = userRepository;
        this.userQueryService = userQueryService;
    }

    public Mono<UserDocument> save(final UserDocument userDocument) {
        return userRepository.save(userDocument)
                .doFirst(() -> log.info("==== try to save a follow user: {}", userDocument));
    }

    public Mono<UserDocument> update(final UserDocument document) {
        return userQueryService.findById(document.id())
                .map(user -> document.toBuilder()
                        .createdAt(user.createdAt())
                        .updatedAt(user.updatedAt())
                        .build())
                .flatMap(userRepository::save)
                .doFirst(() -> log.info("==== try to update a follow info: {}", document) );
    }

    public Mono<Void> delete(final String id) {
        return userQueryService.findById(id)
                .flatMap(userRepository::delete)
                .doFirst(() -> log.info("==== try to delete a follow user with id: {}", id));
    }
}
