package com.test.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Human extends Player {
    private int userBet;
    private String userChoice;

    public Human(String name, List<Card> hand, int money) {
        super(name, money);
        this.hand = hand;
        this.bet = userBet;
    }

    // Human's turn to play
    @Override
    public void play(Deck deck) {
        // Calculate initial total
        for (Card card : hand) {
            total += card.getValue();
        }

        // Human's turn to play
        while (total < 21) {
            if (userChoice.equals("hit")) {
                Card card = deck.dealCard();
                hand.add(card);
                total = calculateTotal();
            } else if (userChoice.equals("stand")) {
                break;
            }
        }
    }

    // Setter for human's bet
    public void setBet(int betAmount) {
        userBet = betAmount;
    }
}
