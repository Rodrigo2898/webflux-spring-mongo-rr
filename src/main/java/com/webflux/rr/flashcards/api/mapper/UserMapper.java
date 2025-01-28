package com.webflux.rr.flashcards.api.mapper;

import com.webflux.rr.flashcards.api.controller.request.UserRequest;
import com.webflux.rr.flashcards.api.controller.response.UserResponse;
import com.webflux.rr.flashcards.domain.document.UserDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request);

    UserResponse toResponse(final UserDocument document);
}
