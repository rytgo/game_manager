package com.game.blackjack;

public class Card {
    private String suit;
    private String rank;
    private int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    // Getter for suit
    public String getSuit() {
        return suit;
    }

    // Getter for rank
    public String getRank() {
        return rank;
    }

    // Getter for value
    public int getValue() {
        return value;
    }
}
