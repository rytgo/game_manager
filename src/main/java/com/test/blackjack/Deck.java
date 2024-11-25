package com.test.blackjack;

import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        initializeDeck();
        shuffleDeck();
    }

    // Shuffle the cards in the deck
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    // Initialize the deck with 52 cards
    public void initializeDeck() {
        String[] suits = {"heart", "diamond", "club", " spade"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
    }

    // Deal a card from the deck
    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.removeFirst();
    }

    // Getter for cards
    public List<Card> getCards() {
        return cards;
    }
}
