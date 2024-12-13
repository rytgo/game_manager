package com.game.blackjack;

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
        while (calculateTotal() <= 16) {
            Card card = deck.dealCard();
            hand.add(card);
            total = calculateTotal();
        }

        // Handle soft 17 rule: If total is 17 and hand contains an Ace, dealer must hit
        if (calculateTotal() == 17 && hasAceInHand() && hand.size() == 2) {
            // Soft 17 rule, dealer hits
            do {
                Card cardOne = deck.dealCard();
                hand.add(cardOne);
                total = calculateTotal();
            } while (calculateTotal() <= 16);
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

