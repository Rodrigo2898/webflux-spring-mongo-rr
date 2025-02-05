package com.webflux.rr.flashcards.domain.exception;

public class EmailAlreadyUsedException extends ReactiveFlashCardsException {

    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
