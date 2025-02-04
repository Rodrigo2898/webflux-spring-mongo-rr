package com.webflux.rr.flashcards.api.mapper;

import com.webflux.rr.flashcards.api.controller.request.UserRequest;
import com.webflux.rr.flashcards.api.controller.response.UserResponse;
import com.webflux.rr.flashcards.domain.document.UserDocument;

public class UserMapper {

    public static UserDocument ToDocument(final UserRequest userRequest) {
        return UserDocument.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .build();
    }

    public static UserDocument toDocument(final UserRequest userRequest, final String id) {
        return UserDocument.builder()
                .id(id)
                .name(userRequest.name())
                .email(userRequest.email())
                .build();
    }

    public static UserResponse toResponse(final UserDocument userDocument) {
        return UserResponse.builder()
                .id(userDocument.id())
                .name(userDocument.name())
                .email(userDocument.email())
                .build();
    }
}
