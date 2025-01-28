package com.webflux.rr.flashcards.api.controller;

import com.webflux.rr.flashcards.api.controller.request.UserRequest;
import com.webflux.rr.flashcards.api.controller.response.UserResponse;
import com.webflux.rr.flashcards.api.mapper.UserMapper;
import com.webflux.rr.flashcards.domain.service.UserService;
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
    private final UserMapper userMapper;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> save(@Valid @RequestBody UserRequest userRequest) {
        return userService.save(userMapper.toDocument(userRequest))
                .doFirst(() -> log.info("==== Saving a user with fallow data: {}", userRequest))
                .map(userMapper::toResponse);
    }
}
