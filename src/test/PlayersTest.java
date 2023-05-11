package src.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.enums.Suits;
import src.model.Cards;
import src.model.Players;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayersTest {

    private Players player;
    private Cards card;
    private Cards card1;
    private Cards card2;
    private Cards card3;

    @BeforeEach
    void setUp() {
        player = new Players("Player 1");
        card = new Cards(Suits.HEARTS, 2);
        card1 = new Cards(Suits.HEARTS, 9);
        card2 = new Cards(Suits.DIAMONDS, 10);
        card3 = new Cards(Suits.SPADES, 5);

    }

    @DisplayName("JUnit test to check if adding a card to a player's hand returns true when hand total is <= 21")
    @Test
    void givenCardWithHandTotalLessThanOrEqualTo21AndTestAddingCardToPlayersHand() {
        assertTrue(player.addCardToPlayersHand(card));
    }

    @DisplayName("JUnit test to check if adding a card to a player's hand increases the cards by 1")
    @Test
    void givenInitialNumCardsAndTestAddingCardToPlayersHand() {
        int initialNumCards = player.getNumCardsInHand();
        player.addCardToPlayersHand(card);
        int newNumCards = player.getNumCardsInHand();
        assertEquals(initialNumCards + 1, newNumCards);
    }

    @DisplayName("JUnit test to check if attempting to add more than 10 cards produces an error message")
    @Test
    void givenMaxNumCardsAndTestAddingCardToPlayersHand() {
        for (int i = 0; i < 10; i++) {
            card3 = new Cards(Suits.SPADES, i + 1);
            player.addCardToPlayersHand(card3);
        }
        card3 = new Cards(Suits.DIAMONDS, 1);
        assertThrows(IllegalStateException.class, () -> player.addCardToPlayersHand(card3));
    }

    @DisplayName("JUnit test to check if adding a card to a hand returns false when the hand total is > 21")
    @Test
    void givenCardWithHandTotalGreaterThan21AndTestAddingCardToPlayersHand() {
        player.addCardToPlayersHand(card1);
        player.addCardToPlayersHand(card2);
        card3 = new Cards(Suits.SPADES, 5);
        assertFalse(player.addCardToPlayersHand(card3));
    }

    @DisplayName("JUnit test to check if getPlayerHandTotal method works as expected")
    @Test
    public void givenCardsToPlayersHandAndTestGetPlayerHandTotalWithAce() {
        card1 = new Cards(Suits.HEARTS, 10);
        card2 = new Cards(Suits.SPADES, 1);
        card3 = new Cards(Suits.CLUBS, 9);

        player.addCardToPlayersHand(card1);
        player.addCardToPlayersHand(card2);
        player.addCardToPlayersHand(card3);

        int expected = 20;
        int actual = player.getPlayerHandTotal();

        assertEquals(expected, actual);
    }

    @DisplayName("JUnit test to check if getPlayerHandTotal method works as expected without Ace card")
    @Test
    public void givenTwoCardsAndTestGetPlayerHandTotalWithoutAce() {
        card1 = new Cards(Suits.HEARTS, 10);
        card3 = new Cards(Suits.CLUBS, 9);
        player.addCardToPlayersHand(card1);
        player.addCardToPlayersHand(card3);

        int expectedTotal = 19;
        int actualTotal = player.getPlayerHandTotal();
        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    @DisplayName("JUnit test to check if getPlayerHandTotal method works as expected without with multiple Aces")
    @Test
    public void givenTwoAcesAndOneMoreCardAndTestGetPlayerHandTotalWithMultipleAces() {
        card1 = new Cards(Suits.HEARTS, 1);
        card2 = new Cards(Suits.SPADES, 1);
        card3 = new Cards(Suits.CLUBS, 10);

        player.addCardToPlayersHand(card1);
        player.addCardToPlayersHand(card2);
        player.addCardToPlayersHand(card3);

        int expectedTotal = 12;
        int actualTotal = player.getPlayerHandTotal();
        assertEquals(expectedTotal, actualTotal);
    }

    @DisplayName("JUnit test to check if printCardsInHand method works as expected")
    @Test
    public void givenTwoCardsAndTestPrintCardsInHandShowFirstCard() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        card1 = new Cards(Suits.HEARTS, 1);
        card2 = new Cards(Suits.DIAMONDS, 10);
        player.addCardToPlayersHand(card1);
        player.addCardToPlayersHand(card2);
        player.printCardsInHand(true);

        String expectedOutput = "\nPlayer 1's cards in hand\n\n" +
                "\tAce of hearts\n\n" +
                "\tten of diamonds\n\n";
        assertEquals(expectedOutput.toLowerCase(), outContent.toString().toLowerCase());
    }

    @DisplayName("JUnit test to check if printCardsInHand method hide dealer card")
    @Test
    public void givenTwoCardsAndTestPrintCardsInHandHideFirstCard() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        card1 = new Cards(Suits.HEARTS, 1);
        card2 = new Cards(Suits.DIAMONDS, 10);
        player.addCardToPlayersHand(card1);
        player.addCardToPlayersHand(card2);
        player.printCardsInHand(false);

        String expectedOutput = "\nPlayer 1's cards in hand\n\n" +
                "\t[hidden]\n" +
                "\tten of diamonds\n\n";
        assertEquals(expectedOutput.toLowerCase(), outContent.toString().toLowerCase());
    }
}
