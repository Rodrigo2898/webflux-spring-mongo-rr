package com.webflux.rr.flashcards.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.rr.flashcards.domain.exception.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class NotFoundHandler extends AbstractHandleException<NotFoundException> {

    public NotFoundHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, NotFoundException ex) {
        return  Mono.fromCallable(() -> {
            preparedExchange(exchange, NOT_FOUND);
            return ex.getMessage();
        }).map(message -> buildError(NOT_FOUND, message))
                .doFirst(() -> log.error("=== NotFoundException", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
