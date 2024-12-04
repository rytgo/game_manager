package com.test.blackjack;

import java.util.List;

public class Human extends Player {
    private int bet;
    private String userChoice;

    public Human(String name, List<Card> hand, int money) {
        super(name, money);
        this.hand = hand;
        this.bet = 0;
    }

    // Human's turn to play
    @Override
    public void play(Deck deck) {
        // Human's turn to play
        if (userChoice.equals("hit")) {
            Card card = deck.dealCard();
            hand.add(card);
            total = calculateTotal();
        }
    }

    // Setter for human's bet
    public void setBet(int betAmount) {
        bet = betAmount;
    }

    // Setter for user's choice
    public void setUserChoice(String choice) {
        userChoice = choice;
    }
}
