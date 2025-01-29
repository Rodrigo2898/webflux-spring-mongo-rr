package com.webflux.rr.flashcards.api.exceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.rr.flashcards.api.controller.response.ProblemResponse;
import com.webflux.rr.flashcards.domain.exception.BaseErrorMessage;
import com.webflux.rr.flashcards.domain.exception.ReactiveFlashCardsException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static com.webflux.rr.flashcards.domain.exception.BaseErrorMessage.*;

@Component
@Order(-2)
public class ApiExceptionHandler implements WebExceptionHandler {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    private final ObjectMapper objectMapper;

    public ApiExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        return Mono.error(ex)
                .onErrorResume(MethodNotAllowedException.class, e -> handleMethodNotAllowedException(exchange, e))
                .onErrorResume(ReactiveFlashCardsException.class, e -> handleReactiveFlashCardsException(exchange, e))
                .onErrorResume(Exception.class, e -> handleException(exchange, e))
                .onErrorResume(JsonProcessingException.class, e -> handleJsonProcessingException(exchange, e))
                .then();
    }

    private Mono<Void> handleMethodNotAllowedException(final ServerWebExchange exchange,
                                                         final MethodNotAllowedException ex) {
        return Mono.fromCallable(() -> {
                    preparedExchange(exchange, HttpStatus.METHOD_NOT_ALLOWED);
                    return GENERIC_METHOD_NOT_ALLOW.getMessage();
                }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.warning("=== MethodNotAllowedException: {}"))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private Mono<Void> handleReactiveFlashCardsException(final ServerWebExchange exchange,
                                                                  final ReactiveFlashCardsException ex) {
        return Mono.fromCallable(() -> {
                    preparedExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.warning("=== ReactiveFlashcardsException: {}"))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private Mono<Void> handleException(final ServerWebExchange exchange, final Exception ex) {
        return Mono.fromCallable(() -> {
            preparedExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
            return GENERIC_EXCEPTION.getMessage();
        }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.warning("=== Exception: {}"))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private Mono<Void> handleJsonProcessingException(final ServerWebExchange exchange, final JsonProcessingException ex) {
        return Mono.fromCallable(() -> {
                    preparedExchange(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                    return GENERIC_EXCEPTION.getMessage();
                }).map(message -> buildError(HttpStatus.INTERNAL_SERVER_ERROR, message))
                .doFirst(() -> log.entering("==== JsonProcessingException: Failed to map exception for the request {} ",
                        exchange.getRequest().getPath().value(), ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private void preparedExchange(final ServerWebExchange exchange, final HttpStatus statusCode) {
        exchange.getResponse().setStatusCode(statusCode);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
    }

    private ProblemResponse buildError(final HttpStatus status, final String errorDescription) {
        return ProblemResponse.builder()
                .status(status.value())
                .errorDescription(errorDescription)
                .build();
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, ProblemResponse problemResponse) {
        return exchange.getResponse()
                .writeWith(Mono.fromCallable(() -> new DefaultDataBufferFactory()
                        .wrap(objectMapper.writeValueAsBytes(problemResponse))));
    }
}
