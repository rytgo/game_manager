package com.test.blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {
    private Deck deck;
    private Human human;
    private Computer computerOne;
    private Computer computerTwo;
    private Dealer dealer;
    private List<Player> players = new ArrayList<>();

    public BlackJack() {
        deck = new Deck();
        human = new Human("userName", new ArrayList<Card>(), 1000);
        computerOne = new Computer("Computer 1", new ArrayList<Card>(), 1000);
        computerTwo = new Computer("Computer 2", new ArrayList<Card>(), 1000);
        dealer = new Dealer("Dealer", new ArrayList<Card>());
        players.addAll(List.of(human, computerOne, computerTwo, dealer));
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
    }

    // Determine the winner of the game
    public String determineWinner(Player player) {
        int dealerTotal = dealer.calculateTotal();
        boolean dealerHasBlackjack = dealer.getHand().size() == 2 && dealerTotal == 21;
        int playerTotal = player.calculateTotal();
        boolean playerHasBlackjack = player.getHand().size() == 2 && playerTotal == 21;
        int playerBet = player.getBet();
        String message = "";

        // Case 1: Player and Dealer both > 21 (both busted)
        if (dealerTotal > 21 && playerTotal > 21) {
            player.setMoney(player.getMoney() - playerBet); // Player loses bet
            message = player.getName() + " busted!";
        }
        // Case 2: Player <= 21, Dealer > 21 (dealer busts)
        else if (dealerTotal > 21) {
            player.setMoney(player.getMoney() + playerBet); // Player wins bet
            message = player.getName() + " won!";
        }
        // Case 3: Player > 21, Dealer <= 21 (player busts)
        else if (playerTotal > 21) {
            player.setMoney(player.getMoney() - playerBet); // Player loses bet
            message = player.getName() + " busted!";
        }
        // Case 4: Dealer <= 21 && Player <= 21
        else {
            // Case 5: Player has Blackjack
            if (playerHasBlackjack && !dealerHasBlackjack) {
                player.setMoney(player.getMoney() + playerBet); // Player wins bet
                message = player.getName() + " have Blackjack! " + player.getName() + " won!";
            }
            // Case 6: Dealer has Blackjack, Player doesn't
            else if (dealerHasBlackjack && !playerHasBlackjack) {
                player.setMoney(player.getMoney() - playerBet); // Player loses bet
                message = "Dealer has Blackjack! " + player.getName() + " lost!";
            }
            // Case 7: Both Player and Dealer have Blackjack (Push)
            else if (playerHasBlackjack) {
                player.setMoney(player.getMoney()); // No change in player's money, it's a tie (push)
                message = player.getName() + " tied!";
            }
            // Case 8: Player has a higher total than the dealer
            else if (playerTotal > dealerTotal) {
                player.setMoney(player.getMoney() + playerBet); // Player wins bet
                message = player.getName() + " won!";
            }
            // Case 9: Player has a lower total than the dealer
            else if (playerTotal < dealerTotal) {
                player.setMoney(player.getMoney() - playerBet); // Player loses bet
                message = player.getName() + " lost!";
            }
            // Case 10: Player and Dealer have the same total (Push)
            else {
                player.setMoney(player.getMoney()); // No change in player's money, it's a tie (push)
                message = player.getName() + " tied!";
            }
        }
        return message;
    }

    // Reset the game
    public void resetGame() {
        deck = new Deck();
        human.getHand().clear();
        computerOne.getHand().clear();
        computerTwo.getHand().clear();
        computerOne.setMoney(computerOne.getMoney());
        computerTwo.setMoney(computerTwo.getMoney());
        dealer.getHand().clear();

        // Check and reset computers money
        if (computerOne.getMoney() <= 0) {
            computerOne.setMoney(1000); // Reset to initial money amount
        }

        if (computerTwo.getMoney() <= 0) {
            computerTwo.setMoney(1000); // Reset to initial money amount
        }
    }


    public Deck getDeck() {
        return this.deck;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
