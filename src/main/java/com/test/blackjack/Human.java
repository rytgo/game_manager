package com.test.blackjack;

import java.util.List;

public class Human extends Player {
    private String userChoice;

    public Human(String name, List<Card> hand, int money) {
        super(name, money);
        this.hand = hand;
    }

    // Human's turn to play
    @Override
    public void play(Deck deck) {
        Card card = deck.dealCard();
        hand.add(card);
        total = calculateTotal();
    }

}
