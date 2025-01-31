package com.webflux.rr.flashcards.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webflux.rr.flashcards.domain.document.UserDocument;
import lombok.Builder;

public record UserResponse(@JsonProperty("id")
                           String id,
                           @JsonProperty("name")
                           String name,
                           @JsonProperty("email")
                           String email) {

    @Builder(toBuilder = true)
    public UserResponse {}

    public static UserResponse toResponse(final UserDocument userDocument) {
        return UserResponse.builder()
                .id(userDocument.id())
                .name(userDocument.name())
                .email(userDocument.email())
                .build();
    }
}
