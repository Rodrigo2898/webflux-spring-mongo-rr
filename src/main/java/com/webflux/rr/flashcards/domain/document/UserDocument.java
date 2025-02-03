package com.webflux.rr.flashcards.domain.document;

import com.webflux.rr.flashcards.api.controller.request.UserRequest;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;

@Document(collection = "users")
public record UserDocument(@Id
                           String id,
                           String name,
                           String email,
                           @CreatedDate
                           @Field("created_at")
                           OffsetDateTime createdAt,
                           @LastModifiedDate
                           @Field("updated_at")
                           OffsetDateTime updatedAt) {
    @Builder(toBuilder = true)
    public UserDocument {}

    public static UserDocument requestToDocument(final UserRequest userRequest) {
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
}
