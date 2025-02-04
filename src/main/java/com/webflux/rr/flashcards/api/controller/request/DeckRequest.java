package com.webflux.rr.flashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;

public record DeckRequest(@JsonProperty("name")
                          @NotBlank
                          @Size(min = 1, max = 50)
                          String name,
                          @JsonProperty("description")
                          @NotBlank
                          @Size(min = 1, max = 255)
                          String description,
                          @JsonProperty("cards")
                          @Valid
                          @NotNull
                          @Size(min = 1)
                          Set<CardRequest> cards) {

    @Builder(toBuilder = true)
    public DeckRequest {}
}
