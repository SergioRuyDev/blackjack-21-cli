package src.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.model.Cards;
import src.model.Deck;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck(2, true);
    }

    @DisplayName("JUnit test to check if the number of cards, and first card dealing.")
    @Test
    void givenDecksAndCheckTheNumberOfCards() {
        Deck deck1 = new Deck();
        assertEquals(52, deck1.getNumbOfCardsInDeck());
        Cards topCard = deck1.dealingNextCard();
        assertNotNull(topCard);

        Deck deck2 = new Deck(2, false);
        assertEquals(104, deck2.getNumbOfCardsInDeck());
        Cards topCard2 = deck2.dealingNextCard();
        assertNotNull(topCard2);
    }

    @DisplayName("JUnit test to check if is is shuffle and the first card is same or not")
    @Test
    public void testShuffleDeck() {
        Deck deck1 = new Deck(2, false);
        Cards topCardBeforeShuffle = deck1.dealingNextCard();

        deck1.shuffleDeck();

        Cards topCardAfterShuffle = deck1.dealingNextCard();
        assertNotEquals(topCardBeforeShuffle, topCardAfterShuffle);
    }

    @DisplayName("JUnit test to check if the dealing card works as expected")
    @Test
    void testDealingNextCardWhenDeckIsEmpty() {
        Deck deck = new Deck(1, false);
        int numCards = deck.getNumbOfCardsInDeck();
        System.out.println("Num cards in deck: " + numCards);

        deck.dealingNextCard();

        Exception exception = assertThrows(IllegalStateException.class, deck::dealingNextCard);
        String expectedMessage = "No cards in the deck.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    } // todo
}
