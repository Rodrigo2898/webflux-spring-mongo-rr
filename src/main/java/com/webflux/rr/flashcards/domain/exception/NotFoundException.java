package com.webflux.rr.flashcards.domain.exception;

public class NotFoundException extends ReactiveFlashCardsException {

    public NotFoundException(String message) {
        super(message);
    }
}
