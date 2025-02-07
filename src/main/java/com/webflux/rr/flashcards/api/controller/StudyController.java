package com.webflux.rr.flashcards.api.controller;

import com.webflux.rr.flashcards.api.controller.request.StudyRequest;
import com.webflux.rr.flashcards.api.controller.response.QuestionResponse;
import com.webflux.rr.flashcards.api.mapper.StudyMapper;
import com.webflux.rr.flashcards.domain.document.StudyDocument;
import com.webflux.rr.flashcards.domain.service.StudyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("studies")
public class StudyController {

    private final StudyService studyService;
    private final Logger log = LoggerFactory.getLogger(StudyController.class);

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<QuestionResponse> start(@Valid @RequestBody final StudyRequest studyRequest) {
        return studyService.start(StudyMapper.toDocument(studyRequest))
                .doFirst(() -> log.info("==== try to create a study with follow request: {}", studyRequest))
                .map(document -> StudyMapper.toResponse(document.getLastQuestionPending()));
    }
}
