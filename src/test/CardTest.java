package src.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.enums.Suits;
import src.model.Cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardTest {

    @DisplayName("JUnit test with constructor and valid values")
    @Test
    void givenValidValuesToConstructor() {
        Cards card = new Cards(Suits.SPADES, 6);

        assertEquals(Suits.SPADES, card.getCardSuit());
        assertEquals(6, card.getCardNumb());
    }

    @DisplayName("JUnit test with constructor and invalid values")
    @Test
    void givenInvalidValuesToConstructorAndThrowIllegalException() {

        assertThrows(IllegalArgumentException.class, () -> new Cards(Suits.SPADES, 15));
    }
}
