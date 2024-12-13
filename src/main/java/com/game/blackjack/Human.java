package com.game.blackjack;

import java.util.List;

public class Human extends Player {
    private String userChoice;

    public Human(String name, List<Card> hand, int money, int bet) {
        super(name, money, bet);
        this.hand = hand;
    }

    // Human's turn to play
    @Override
    public void play(Deck deck) {
        Card card = deck.dealCard();
        hand.add(card);
        total = calculateTotal();
    }

    // Check for valid bet amount
    public boolean isValidBet(int amount) {
        return amount > 0 && amount <= money;
    }

}
