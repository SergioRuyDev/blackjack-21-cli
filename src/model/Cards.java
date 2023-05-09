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

            System.err.println(suitNumb + " is not a valid card number\n");
            System.exit(1);
        }
    }

    public Suits getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(Suits cardSuit) {
        this.cardSuit = cardSuit;
    }

    public int getCardNumb() {
        return cardNumb;
    }

    public void setCardNumb(int cardNumb) {
        this.cardNumb = cardNumb;
    }

    public String[] getNumbString() {
        return numbString;
    }

    public void setNumbString(String[] numbString) {
        this.numbString = numbString;
    }

    @Override
    public String toString() {
        return this.numbString[this.cardNumb - 1] + " of " + this.cardSuit.toString();
    }
}
