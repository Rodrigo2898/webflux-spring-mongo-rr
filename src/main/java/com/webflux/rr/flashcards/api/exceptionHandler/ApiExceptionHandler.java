package com.webflux.rr.flashcards.api.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webflux.rr.flashcards.domain.exception.NotFoundException;
import com.webflux.rr.flashcards.domain.exception.ReactiveFlashCardsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;


@Component
@Order(-2)
public class ApiExceptionHandler implements WebExceptionHandler {

    private final MethodNotAllowHandler methodNotAllowHandler;
    private final NotFoundHandler notFoundHandler;
    private final ConstraintViolationHandler constraintViolationHandler;
    private final WebExchangeBindHandler webExchangeBindHandler;
    private final ResponseStatusHandler responseStatusHandler;
    private final ReactiveFlashCardsHandler reactiveFlashCardsHandler;
    private final GenericHandler genericHandler;
    private final JsonProcessingHandler jsonProcessingHandler;

    public ApiExceptionHandler(MethodNotAllowHandler methodNotAllowHandler,
                               NotFoundHandler notFoundHandler,
                               ConstraintViolationHandler constraintViolationHandler,
                               WebExchangeBindHandler webExchangeBindHandler,
                               ResponseStatusHandler responseStatusHandler,
                               ReactiveFlashCardsHandler reactiveFlashCardsHandler,
                               GenericHandler genericHandler,
                               JsonProcessingHandler jsonProcessingHandler) {
        this.methodNotAllowHandler = methodNotAllowHandler;
        this.notFoundHandler = notFoundHandler;
        this.constraintViolationHandler = constraintViolationHandler;
        this.webExchangeBindHandler = webExchangeBindHandler;
        this.responseStatusHandler = responseStatusHandler;
        this.reactiveFlashCardsHandler = reactiveFlashCardsHandler;
        this.genericHandler = genericHandler;
        this.jsonProcessingHandler = jsonProcessingHandler;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return Mono.error(ex)
                .onErrorResume(MethodNotAllowedException.class, e -> methodNotAllowHandler.handleException(exchange, e))
                .onErrorResume(NotFoundException.class, e -> notFoundHandler.handleException(exchange, e))
                .onErrorResume(ConstraintViolationException.class, e -> constraintViolationHandler.handleException(exchange, e))
                .onErrorResume(WebExchangeBindException.class, e -> webExchangeBindHandler.handleException(exchange, e))
                .onErrorResume(ResponseStatusException.class, e -> responseStatusHandler.handleException(exchange, e))
                .onErrorResume(ReactiveFlashCardsException.class, e -> reactiveFlashCardsHandler.handleException(exchange, e))
                .onErrorResume(Exception.class, e -> genericHandler.handleException(exchange, e))
                .onErrorResume(JsonProcessingException.class, e -> jsonProcessingHandler.handleException(exchange, e))
                .then();
    }
}
