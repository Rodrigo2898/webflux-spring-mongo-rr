package com.webflux.rr.flashcards.domain.service;

import com.webflux.rr.flashcards.domain.document.UserDocument;
import com.webflux.rr.flashcards.domain.exception.EmailAlreadyUsedException;
import com.webflux.rr.flashcards.domain.exception.NotFoundException;
import com.webflux.rr.flashcards.domain.repository.UserRepository;
import com.webflux.rr.flashcards.domain.service.query.UserQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.webflux.rr.flashcards.domain.exception.BaseErrorMessage.EMAIL_ALREADY_USED;

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
        return userQueryService.findByEmail(userDocument.email())
                .doFirst(() -> log.info("==== try to save a follow user: {}", userDocument))
                .filter(Objects::isNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(
                        new EmailAlreadyUsedException(EMAIL_ALREADY_USED.params(userDocument.email()).getMessage())
                )))
                .onErrorResume(NotFoundException.class, e -> userRepository.save(userDocument));
    }

    public Mono<UserDocument> update(final UserDocument document) {
        return verifyEmail(document)
                .then(userQueryService.findById(document.id())
                        .map(user -> document.toBuilder()
                                .createdAt(user.createdAt())
                                .updatedAt(user.updatedAt())
                                .build())
                        .flatMap(userRepository::save)
                        .doFirst(() -> log.info("==== try to update a follow info: {}", document)));
    }

    private Mono<Void> verifyEmail(final UserDocument userDocument) {
        return userQueryService.findByEmail(userDocument.email())
                .filter(stored -> stored.id().equals(userDocument.id()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new EmailAlreadyUsedException(EMAIL_ALREADY_USED
                        .params(userDocument.email()).getMessage()))))
                .onErrorResume(NotFoundException.class, e -> Mono.empty())
                .then();
    }

    public Mono<Void> delete(final String id) {
        return userQueryService.findById(id)
                .flatMap(userRepository::delete)
                .doFirst(() -> log.info("==== try to delete a follow user with id: {}", id));
    }
}
