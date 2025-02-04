package com.webflux.rr.flashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record CardRequest(@JsonProperty("front")
                          @NotBlank
                          @Size(min = 1, max = 50)
                          String front,
                          @JsonProperty("back")
                          @NotBlank
                          @Size(min = 1, max = 50)
                          String back) {

    @Builder(toBuilder = true)
    public CardRequest {}
}
