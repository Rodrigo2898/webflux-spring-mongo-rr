package com.webflux.rr.flashcards.domain.document;

public record Question(String asked,
                       String answered,
                       String expected) {
}
