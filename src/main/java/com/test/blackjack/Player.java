package com.test.blackjack;

import java.util.List;

abstract class Player {
    protected String name;
    protected List<Card> hand;
    protected int money;
    protected int bet;
    protected int total;

    public Player(String name, int money) {
        this.name = name;
        this.money = 1000;
    }

    public Player(String name) {
        this.name = name;
    }

    // Getter for money
    public int getMoney() {
        return money;
    }

    // Setter for money
    public void setMoney(int money) {
        this.money = money;
    }

    // Getter for bet
    public int getBet() {
        return bet;
    }

    // Getter for hand
    protected List<Card> getHand() {
        return hand;
    }


    // Getter for name
    public String getName() {
        return name;
    }

    // Abstract method for a player's turn to play
    public abstract void play(Deck deck);

    // Adjust for aces in the hand and calculate hand total
    protected int calculateTotal() {
        int aceCount = 0;

        // Calculate initial total and count aces
        for (Card card : hand) {
            total += card.getValue();
            if (card.getRank().equals("ace")) {
                aceCount++;
            }
        }

        // Adjust for aces if total > 21
        while (total > 21 && aceCount > 0) {
            total -= 10; // Reduce ace value from 11 to 1
            aceCount--;
        }
        return total;
    }
}
