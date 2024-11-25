package com.test.blackjack;

import java.util.ArrayList;

public class BlackJack {
    private Deck deck;
    private Human human;
    private Computer computerOne;
    private Computer computerTwo;
    private Dealer dealer;
    private String userName;
    private int userBet;

    public BlackJack() {
        deck = new Deck();
        human = new Human(userName,  new ArrayList<Card>(), 1000);
        computerOne = new Computer("Computer 1", new ArrayList<Card>(), 1000);
        computerTwo = new Computer("Computer 2",  new ArrayList<Card>(), 1000);
        dealer = new Dealer("Dealer", new ArrayList<Card>());
    }

    public void playGame() {
        // Create bets for each player
        human.setBet(userBet);
        computerOne.randomBet();
        computerTwo.randomBet();

        // Add two cards to each player's hand
        human.getHand().add(deck.dealCard());
        human.getHand().add(deck.dealCard());

        computerOne.getHand().add(deck.dealCard());
        computerOne.getHand().add(deck.dealCard());

        computerTwo.getHand().add(deck.dealCard());
        computerTwo.getHand().add(deck.dealCard());

        dealer.getHand().add(deck.dealCard());
        dealer.getHand().add(deck.dealCard());

        // Play the game
        human.play(deck);
        computerOne.play(deck);
        computerTwo.play(deck);
        dealer.play(deck);
    }
}
