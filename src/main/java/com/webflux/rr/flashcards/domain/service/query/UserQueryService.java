package com.webflux.rr.flashcards.domain.service.query;

import com.webflux.rr.flashcards.domain.document.UserDocument;
import com.webflux.rr.flashcards.domain.exception.NotFoundException;
import com.webflux.rr.flashcards.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.webflux.rr.flashcards.domain.exception.BaseErrorMessage.USER_NOT_FOUND;

@Service
public class UserQueryService {

    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

    public UserQueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserDocument> findById(final String id) {
        return userRepository.findById(id)
                .doFirst(() -> log.info("=== try to find user with id: {}", id))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(
                        () -> Mono.error(new NotFoundException(USER_NOT_FOUND.params(id).getMessage()))
                ));
    }
}
