package com.test.blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {
    private Deck deck;
    private Human human;
    private Computer computerOne;
    private Computer computerTwo;
    private Dealer dealer;
    private String userName;
    private int userBet;
    private List<Player> players = new ArrayList<>();
    private List <Card> dealtCards = new ArrayList<>();

    public BlackJack() {
        deck = new Deck();
        human = new Human("userName",  new ArrayList<Card>(), 1000);
        computerOne = new Computer("Computer 1", new ArrayList<Card>(), 1000);
        computerTwo = new Computer("Computer 2",  new ArrayList<Card>(), 1000);
        dealer = new Dealer("Dealer", new ArrayList<Card>());
        players.addAll(List.of(human, computerOne, computerTwo));
    }

    // Getters for players
    public Human getHuman() {
        return human;
    }

    public Computer getComputerOne() {
        return computerOne;
    }

    public Computer getComputerTwo() {
        return computerTwo;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void dealCard() {

        // Add two cards to each player's hand
        human.getHand().add(deck.dealCard());
        human.getHand().add(deck.dealCard());

        computerOne.getHand().add(deck.dealCard());
        computerOne.getHand().add(deck.dealCard());

        computerTwo.getHand().add(deck.dealCard());
        computerTwo.getHand().add(deck.dealCard());

        dealer.getHand().add(deck.dealCard());
        dealer.getHand().add(deck.dealCard());

        dealtCards.addAll(human.getHand());
        dealtCards.addAll(computerOne.getHand());
        dealtCards.addAll(computerTwo.getHand());
        dealtCards.addAll(dealer.getHand());

//        // Play the game
//        human.play(deck);
//        computerOne.play(deck);
//        computerTwo.play(deck);
//        dealer.play(deck);
    }

    // Getter for dealt cards
    public List<Card> getDealtCards() {
        return dealtCards;
    }

    public void determineWinner() {
        int dealerTotal = dealer.calculateTotal();

        for (Player player : players) {
            int playerTotal = player.calculateTotal();
            int playerBet = player.getBet();

            // Case 1: Player is busted
            if (playerTotal > 21 && dealerTotal <= 21) {
                player.setMoney(player.getMoney() - playerBet);  // Player loses their bet
            }
            // Case 2: Both player and dealer are busted
            else if (playerTotal > 21) {
                player.setMoney(player.getMoney() - playerBet);  // Player loses their bet
            }
            // Case 3: Only dealer is busted
            else if (dealerTotal > 21) {
                player.setMoney(player.getMoney() + playerBet);  // Player wins their bet
            }
            // Case 4: Player has a higher total than the dealer (no one busted)
            else if (playerTotal > dealerTotal) {
                player.setMoney(player.getMoney() + playerBet);  // Player wins their bet
            }
            // Case 5: Dealer has a higher total than the player (no one busted)
            else if (playerTotal < dealerTotal) {
                player.setMoney(player.getMoney() - playerBet);  // Player loses their bet
            }
            // Case 6: Both player and dealer have the same total
            else {
                player.setMoney(player.getMoney());  // No change in money
            }
        }
    }
}
