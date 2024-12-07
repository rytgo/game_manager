package com.test.blackjack;

import java.util.List;

abstract class Player {
    protected String name;
    protected List<Card> hand;
    protected int money;
    protected int bet;
    protected int total;

    protected Player(String name, int money, int bet) {
        this.name = name;
        this.money = money;
        this.bet = bet;
    }

    public Player(String name) {
        this.name = name;
    }

    // Setter for name
    public void setName(String name) {
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

    // Setter for bet
    public void setBet(int amount) {
        bet = amount;
    }

    // Getter for hand
    protected List<Card> getHand() {
        return hand;
    }

    // Setter for hand
    protected void setHand(List<Card> hand) {
        this.hand = hand;
    }

    // Getter for initial total
    public int getTotal() {
        return total;
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
        total = 0;
        for (Card card : this.getHand()) {
            total += card.getValue();
            if (card.getRank().equals("a")) {
                aceCount++;
            }
        }

        // Adjust for aces if total > 21 and we need to treat them as 1
        while (total > 21 && aceCount > 0) {
            total -= 10; // Reduce ace value from 11 to 1
            aceCount--;
        }
        return total;
    }

}
