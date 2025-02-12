package com.webflux.rr.flashcards.domain.document;

import lombok.Builder;

import java.time.OffsetDateTime;

public record Question(String asked,
                       OffsetDateTime askedIn,
                       String answered,
                       OffsetDateTime answeredIn,
                       String expected) {

    @Builder(toBuilder = true)
    public Question {
    }
}
