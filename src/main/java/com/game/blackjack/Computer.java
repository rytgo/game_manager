package com.game.blackjack;

import java.util.List;

public class Computer extends Player {
    public Computer(String name, List<Card> hand, int money, int bet) {
        super(name, money, bet);
        this.hand = hand;
    }

    // Computer's turn to play
    @Override
    public void play(Deck deck) {
        while (calculateTotal() < 16) {
            Card card = deck.dealCard();
            hand.add(card);
            total = calculateTotal();
        }
    }

    // Randomize the computer's bet
    public int randomBet() {
        int[] bets = {10, 20, 50, 100};
        bet = bets[(int) (Math.random() * bets.length)];
        return bet;
    }
}
