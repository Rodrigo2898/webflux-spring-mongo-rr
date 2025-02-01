package com.webflux.rr.flashcards.api.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.webflux.rr.flashcards.domain.exception.BaseErrorMessage.GENERIC_EXCEPTION;

@Component
public class JsonProcessingHandler extends AbstractHandleException<JsonProcessingException> {

    protected JsonProcessingHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, JsonProcessingException ex) {
        return Mono.fromCallable(() -> {
                    preparedExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.error("==== JsonProcessingException: Failed to map exception for the request {} ",
                        exchange.getRequest().getPath().value(), ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
