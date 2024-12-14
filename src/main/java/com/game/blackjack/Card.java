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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Card card = (Card) obj;
        return value == card.value && suit.equals(card.suit) && rank.equals(card.rank);
    }
}
