package com.webflux.rr.flashcards.domain.service.query;

import com.webflux.rr.flashcards.domain.document.StudyDocument;
import com.webflux.rr.flashcards.domain.exception.NotFoundException;
import com.webflux.rr.flashcards.domain.repository.StudyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.webflux.rr.flashcards.domain.exception.BaseErrorMessage.STUDY_NOT_FOUND;

@Service
public class StudyQueryService {

    private final StudyRepository studyRepository;
    private final Logger log = LoggerFactory.getLogger(StudyQueryService.class);

    public StudyQueryService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public Mono<StudyDocument> findPendingStudyByUserIdAndDeckId(final String userId, final String deckId) {
        return studyRepository.findByUserIdAndCompleteFalseAndStudyDeck_DeckId(userId, deckId)
                .doFirst(() -> log.info("==== Try to get pending study with userId {} and deckId {}", userId, deckId))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new NotFoundException(STUDY_NOT_FOUND.params(userId, deckId).getMessage()))));
    }
}
