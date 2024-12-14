package com.test;

import com.game.blackjack.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BlackJackTest {

    private BlackJack blackJack;

    @BeforeEach
    public void setUp() {
        // Initialize the BlackJack instance
        blackJack = new BlackJack();
    }

    @Test
    public void testInitialSetup() {
        // Ensure all players are initialized
        assertNotNull(blackJack.getHuman());
        assertNotNull(blackJack.getComputerOne());
        assertNotNull(blackJack.getComputerTwo());
        assertNotNull(blackJack.getDealer());

        // Ensure the deck is initialized
        assertNotNull(blackJack.getDeck());
        assertEquals(52, blackJack.getDeck().getRemainingCards()); // Assuming a standard deck size
    }

    @Test
    public void testDealCard() {
        // Deal cards to all players
        blackJack.dealCard();

        // Ensure each player has two cards
        assertEquals(2, blackJack.getHuman().getHand().size());
        assertEquals(2, blackJack.getComputerOne().getHand().size());
        assertEquals(2, blackJack.getComputerTwo().getHand().size());
        assertEquals(2, blackJack.getDealer().getHand().size());

        // Ensure deck size decreases correctly
        assertEquals(52 - (4 * 2), blackJack.getDeck().getRemainingCards());
    }

    @Test
    public void testDetermineWinner_PlayerWins() {
        Player player = blackJack.getHuman();
        player.getHand().add(new Card("Hearts", "10", 10));
        player.getHand().add(new Card("Spades", "10", 10));

        Dealer dealer = blackJack.getDealer();
        dealer.getHand().add(new Card("Hearts", "5", 5));
        dealer.getHand().add(new Card("Clubs", "8", 5));

        player.setBet(100);

        String result = blackJack.determineWinner(player);
        assertEquals(player.getMoney(), 1100); // Player wins the bet
        assertEquals("userName won!", result);
    }

    @Test
    public void testResetGame() {
        blackJack.getHuman().setMoney(500);
        blackJack.resetGame();

        // Ensure money is reset for all players
        assertEquals(1000, blackJack.getHuman().getMoney());
        assertEquals(1000, blackJack.getComputerOne().getMoney());
        assertEquals(1000, blackJack.getComputerTwo().getMoney());

        // Ensure all hands are cleared
        assertEquals(0, blackJack.getHuman().getHand().size());
        assertEquals(0, blackJack.getComputerOne().getHand().size());
        assertEquals(0, blackJack.getComputerTwo().getHand().size());
        assertEquals(0, blackJack.getDealer().getHand().size());

        // Ensure deck is reset
        assertEquals(52, blackJack.getDeck().getRemainingCards());
    }

}
