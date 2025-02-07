package com.webflux.rr.flashcards.api.mapper;

import com.webflux.rr.flashcards.api.controller.request.StudyRequest;
import com.webflux.rr.flashcards.api.controller.response.QuestionResponse;
import com.webflux.rr.flashcards.domain.document.Question;
import com.webflux.rr.flashcards.domain.document.StudyDeck;
import com.webflux.rr.flashcards.domain.document.StudyDocument;

import java.util.HashSet;

public class StudyMapper {

    public static StudyDocument toDocument(final StudyRequest studyRequest) {
        StudyDeck studyDeck = StudyDeck.builder()
                .deckId(studyRequest.deckId())
                .cards(new HashSet<>())
                .build();

        return StudyDocument.builder()
                .studyDeck(studyDeck)
                .build();
    }

    public static QuestionResponse toResponse(final Question question) {
        return QuestionResponse.builder()
                .asked(question.asked())
                .askedIn(question.askedIn())
                .build();
    }
}
