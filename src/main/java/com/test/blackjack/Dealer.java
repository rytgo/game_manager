package com.test.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Dealer extends Player {
    public Dealer(String name, List<Card> hand) {
        super(name);
        this.hand = hand;
    }

    // Dealer's turn to play
    @Override
    public void play(Deck deck) {
        // Draw cards until total is 17 or higher
        while (total <= 16) {
            Card card = deck.dealCard();
            hand.add(card);
            total = calculateTotal();
        }

        // Handle soft 17 rule: If total is 17 and hand contains an Ace, dealer must hit
        if (total == 17 && hasAceInHand()) {
            // Soft 17 rule, dealer hits
            Card card = deck.dealCard();
            hand.add(card);
            total = calculateTotal();
        }
    }

    // Helper method to check if dealer has an ace in hand
    private boolean hasAceInHand() {
        for (Card card : hand) {
            if (card.getRank().equals("a")) {
                return true;
            }
        }
        return false;
    }
}

