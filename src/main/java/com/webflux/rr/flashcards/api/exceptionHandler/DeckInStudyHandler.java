package com.webflux.rr.flashcards.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.rr.flashcards.domain.exception.DeckInStudyException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CONFLICT;

@Component
public class DeckInStudyHandler extends AbstractHandleException<DeckInStudyException> {

    protected DeckInStudyHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, DeckInStudyException ex) {
        return Mono.fromCallable(() -> {
            preparedExchange(exchange, CONFLICT);
            return ex.getMessage();
        }).map(message -> buildError(CONFLICT, message))
                .doFirst(() -> log.error("==== DeckInStudyException"))
                .flatMap(response -> writeResponse(exchange, response));
    }
}
