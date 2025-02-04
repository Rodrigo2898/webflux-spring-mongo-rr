package com.webflux.rr.flashcards.api.mapper;

import com.webflux.rr.flashcards.api.controller.request.DeckRequest;
import com.webflux.rr.flashcards.api.controller.response.CardResponse;
import com.webflux.rr.flashcards.api.controller.response.DeckResponse;
import com.webflux.rr.flashcards.domain.document.Card;
import com.webflux.rr.flashcards.domain.document.DeckDocument;

import java.util.Set;
import java.util.stream.Collectors;


public class DeckMapper {

    public static DeckDocument toDocument(final DeckRequest deckRequest) {
        Set<Card> mapperCards = deckRequest.cards().stream()
                .map(CardMapper::toCard)
                .collect(Collectors.toSet());

        return DeckDocument.builder()
                .name(deckRequest.name())
                .description(deckRequest.description())
                .cards(mapperCards)
                .build();
    }

    public static DeckResponse toResponse(final DeckDocument deckDocument) {
        Set<CardResponse> mapperCards = deckDocument.cards().stream()
                .map(CardMapper::toResponse)
                .collect(Collectors.toSet());

        return DeckResponse.builder()
                .id(deckDocument.id())
                .description(deckDocument.description())
                .cards(mapperCards)
                .build();
    }
}
