package com.test.blackjack;

import java.util.List;

public class Dealer extends Player {
    public Dealer(String name, List<Card> hand) {
        super(name, hand, 0, 0);
    }
}
