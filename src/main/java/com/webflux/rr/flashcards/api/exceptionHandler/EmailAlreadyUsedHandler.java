package com.webflux.rr.flashcards.api.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.rr.flashcards.domain.exception.EmailAlreadyUsedException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class EmailAlreadyUsedHandler extends AbstractHandleException<EmailAlreadyUsedException> {

    protected EmailAlreadyUsedHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, EmailAlreadyUsedException ex) {
        return  Mono.fromCallable(() -> {
                    preparedExchange(exchange, BAD_REQUEST);
                    return ex.getMessage();
                }).map(message -> buildError(BAD_REQUEST, message))
                .doFirst(() -> log.error("=== EmailAlreadyUsedException", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }
}
