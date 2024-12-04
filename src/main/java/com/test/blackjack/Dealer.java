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

        while (total <= 16) {
            Card card = deck.dealCard();
            hand.add(card);
            total = calculateTotal();
        }

        // Handle soft 17 case
        boolean isSoft17 = false;
        while (getHand().size() == 2) {
            for (Card card : hand) {
                if (card.getRank().equals("a") && total == 17) {
                    isSoft17 = true;
                    break;
                }
            }
        }

        // If soft 17, dealer hits
        if (isSoft17) {
            Card card = deck.dealCard();
            hand.add(card);
            total = calculateTotal();
        }
    }
}
