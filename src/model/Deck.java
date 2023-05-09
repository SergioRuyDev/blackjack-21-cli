package src.model;

import src.enums.Suits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class for represent a dynamic Deck of cards
 * Each pack = 52 cards;
 * A Deck = One or more packs;
 * @author Sergio Ruy
 *
 */

public class Deck {

    private ArrayList<Cards> cardsInDeck;
    private int numbOfCardsInDeck;
    private int onePack = 52;

    /**
     * constructor for shuffle, consist in 1 pack = 52 cards
     */

    public Deck() {
        this(1, true);
    }

    /**
     *
     * Deck constructor to define number of cards in a deck and whether it should be shuffled or not
     *
     * @param numPacks
     * @param shuffle
     */
    public Deck(int numPacks, boolean shuffle) {

        this.numbOfCardsInDeck = numPacks * this.onePack;
        this.cardsInDeck = new ArrayList<>(this.numbOfCardsInDeck);

        // For each deck
        for (int deck = 0; deck < numPacks; deck++) {

            //For each suit
            for (int suit = 0; suit < 4; suit++) {

                //For each numbers
                for (int num = 0; num < 13; num++) {
                    this.cardsInDeck.add(new Cards(Suits.values()[suit], num));
                }
            }
        }

        //shuffle
        if (shuffle) {
            this.shuffleDeck();
        }
    }

    // Requirement for Shuffle a deck
    public void shuffleDeck() {
        Collections.shuffle(cardsInDeck, new Random());
    }

    // dealing a card from deck
    public Cards dealingNextCard() {
        Cards topCard = this.cardsInDeck.get(0);

        // removing the card
        this.cardsInDeck.remove(0);
        this.numbOfCardsInDeck--;

        return topCard;
    }

}
