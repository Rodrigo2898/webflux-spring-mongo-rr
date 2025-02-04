package com.webflux.rr.flashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Set;

public record DeckResponse(@JsonProperty("id")
                           String id,
                           @JsonProperty("description")
                           String description,
                           @JsonProperty("cards")
                           Set<CardResponse> cards) {

    @Builder(toBuilder = true)
    public DeckResponse {}
}
