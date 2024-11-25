package com.test.blackjack;

import java.util.List;

abstract class Player {
    protected String name;
    protected List<Card> hand;
    protected int money;
    protected int bet;

    public Player(String name, List<Card> hand, int money, int bet) {
        this.name = name;
        this.hand = hand;
        this.money = money;
        this.bet = bet;
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
    public void setBet(int bet) {
        this.bet = bet;
    }

    // Getter for hand
    public List<Card> getHand() {
        return hand;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Add a card to the player's hand
    public void addCard(Card card) {
        hand.add(card);
    }

    // A player's turn to play
    public void play(Deck deck) {
        int total = 0;
        for (Card card : hand) {
            total += card.getValue();
        }
        while (total < 16) {
            Card card = deck.dealCard();
            if (card == null) {
                break;
            }
            hand.add(card);
            total += card.getValue();
        }
    }
}
