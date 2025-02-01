package com.webflux.rr.flashcards.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.webflux.rr.flashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;

@Component
public class ResponseStatusHandler extends AbstractHandleException<ResponseStatusException> {

    protected ResponseStatusHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, ResponseStatusException ex) {
        return Mono.fromCallable(() -> {
                    preparedExchange(exchange, HttpStatus.NOT_FOUND);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(HttpStatus.NOT_FOUND, message))
                .doFirst(() -> log.error("=== ResponseStatusException", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
