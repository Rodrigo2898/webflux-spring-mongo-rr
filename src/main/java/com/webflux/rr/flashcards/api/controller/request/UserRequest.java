package com.webflux.rr.flashcards.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record UserRequest(@JsonProperty("name")
                          @NotBlank
                          @Size(min = 1, max = 50)
                          String name,
                          @JsonProperty("email")
                          @NotBlank
                          @Email
                          String email) {

    @Builder(toBuilder = true)
    public UserRequest {}
}
