package com.webflux.rr.flashcards.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.rr.flashcards.domain.exception.ReactiveFlashCardsException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.webflux.rr.flashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;

@Component
public class ReactiveFlashCardsHandler extends AbstractHandleException<ReactiveFlashCardsException> {

    protected ReactiveFlashCardsHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, ReactiveFlashCardsException ex) {
        return Mono.fromCallable(() -> {
                    preparedExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("=== ReactiveFlashcardsException ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
