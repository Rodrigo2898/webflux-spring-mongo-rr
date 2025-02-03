package com.webflux.rr.flashcards.api.controller;

import com.webflux.rr.flashcards.api.controller.request.UserRequest;
import com.webflux.rr.flashcards.api.controller.response.UserResponse;
import com.webflux.rr.flashcards.core.validation.MongoId;
import com.webflux.rr.flashcards.domain.document.UserDocument;
import com.webflux.rr.flashcards.domain.service.UserService;
import com.webflux.rr.flashcards.domain.service.query.UserQueryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserQueryService userQueryService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, UserQueryService userQueryService) {
        this.userService = userService;
        this.userQueryService = userQueryService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> save(@Valid @RequestBody UserRequest userRequest) {
        return userService.save(UserDocument.requestToDocument(userRequest))
                .doFirst(() -> log.info("==== Saving a user with fallow data: {}", userRequest))
                .map(UserResponse::toResponse);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<UserResponse> findById(@PathVariable @Valid @MongoId(message = "${userController.id}") String id) {
        return userQueryService.findById(id)
                .doFirst(() -> log.info("==== Finding a user with follow id {}", id))
                .map(UserResponse::toResponse);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE, value = "{id}")
    public Mono<UserResponse> update(@PathVariable @Valid @MongoId(message = "${userController.id}") String id,
                                    @Valid @RequestBody UserRequest userRequest) {
        return userService.update(UserDocument.toDocument(userRequest, id))
                .doFirst(() -> log.info("==== Updating a user with follow info [body: {}, id: {}]", userRequest, id))
                .map(UserResponse::toResponse);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable @Valid @MongoId(message = "${userController.id}") String id) {
        return userService.delete(id)
                .doFirst(() -> log.info("==== Deleting a user with follow user id {}", id));
    }
}
