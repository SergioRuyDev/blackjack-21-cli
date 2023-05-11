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

    @DisplayName("JUnit test to check if Dealing a card reduce the number of cards in deck by 1")
    @Test
    void givenInitialNumberOfCardsAndTestDealingNextCard() {
        int initialNumOfCards = deck.getNumbOfCardsInDeck();
        deck.dealingNextCard();
        int newNumOfCards = deck.getNumbOfCardsInDeck();
        assertEquals(initialNumOfCards - 1, newNumOfCards);
    }

    @DisplayName("JUnit test to check if to deal a card from an empty deck throw an IllegalStateException")
    @Test
    void givenEmptyDeckDealingNextCardFromEmptyDeck() {
        deck = new Deck(0, true); // create an empty deck
        assertThrows(IllegalStateException.class, () -> deck.dealingNextCard());
    }
}
