package com.webflux.rr.flashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record CardResponse(@JsonProperty("id")
                           String id,
                           @JsonProperty("front")
                           String front,
                           @JsonProperty("back")
                           String back) {

    @Builder(toBuilder = true)
    public CardResponse {}
}
