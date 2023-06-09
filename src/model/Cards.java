package src.model;

/**
 *
 * Class for Cards suit and numbers
 *
 * @author Sergio Ruy
 *
 */

import src.enums.Suits;

public class Cards {

    private Suits cardSuit;
    private int cardNumb;
    private String[] numbString = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Jack", "Queen", "King"};

    /**
     * Cards constructor
     * @param suitType
     * @param suitNumb
     */
    public Cards(Suits suitType, int suitNumb) {

        this.cardSuit = suitType;

        if (suitNumb >= 1 && suitNumb <= 13) {
            this.cardNumb = suitNumb;
        } else {
            throw new IllegalArgumentException(suitNumb + " is not a valid card number");
        }

    }

    public Suits getCardSuit() {
        return cardSuit;
    }

    public int getCardNumb() {
        return cardNumb;
    }

    @Override
    public String toString() {
        return this.numbString[this.cardNumb - 1] + " of " + this.cardSuit.toString();
    }
}
