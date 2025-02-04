package com.webflux.rr.flashcards.api.mapper;

import com.webflux.rr.flashcards.api.controller.request.CardRequest;
import com.webflux.rr.flashcards.api.controller.response.CardResponse;
import com.webflux.rr.flashcards.domain.document.Card;

public class CardMapper {

    public static Card toCard(final CardRequest cardRequest) {
        return Card.builder()
                .front(cardRequest.front())
                .back(cardRequest.back())
                .build();
    }


    public static CardResponse toResponse(final Card card) {
        return CardResponse.builder()
                .id(card.id())
                .front(card.front())
                .back(card.back())
                .build();
    }
}
