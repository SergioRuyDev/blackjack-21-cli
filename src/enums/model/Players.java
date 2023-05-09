package src.enums.model;

/**
 * Class for represent the players (dealer also)
 */

import java.util.ArrayList;

public class Players {

    private String playerName;
    private ArrayList<Cards> playerHand;
    private int numCardsInHand;

    public Players(String name) {
        this.playerName = name;
        this.emptyHand();
    }

    public void emptyHand() {
        this.playerHand.clear();
        this.numCardsInHand = 0;
    }

    /**
     * Method for count and limit the hand of the players
     * @param card
     */
    public boolean addCardToPlayersHand(Cards card) {
        if (this.numCardsInHand == 10) {

            System.err.printf("%s's hand already has 10 cards and cannot add more", this.playerName);
            System.exit(1);
        }

        this.playerHand.add(card);
        this.numCardsInHand++;

        return (this.getPlayerHandTotal() <= 21);
    }

    /**
     * Method for limit the Aces
     */
    public int getPlayerHandTotal() {
        int handTotal = 0;
        int cardNum;
        int numAces = 0;

        for (int card = 0; card < this.numCardsInHand; card++) {
            cardNum = this.playerHand.get(card).getCardNumb();

            if (cardNum == 1) { // Ace

                numAces++;
                handTotal += 11;
            } else if (cardNum >= 10) {

                handTotal += 10;
            } else {

                handTotal += cardNum;
            }
        }

        while (handTotal > 21 && numAces > 0) {

            handTotal -= 10;
            numAces--;
        }

        return handTotal;
    }


    /**
     * Method for print the cards and hide the dealer first card
     * @param showFirstCard
     */
    public void printCardsInHand(boolean showFirstCard) {

        System.out.printf("\n%s's cards in hand\n\n", this.playerName);
        for (int c = 0; c < this.numCardsInHand; c++) {

            if (!showFirstCard && c == 0) {

                System.out.printf("\t[hidden]\n");
            } else {
                System.out.printf("\t%s\n\n", this.playerHand.get(c));
            }
        }
    }

}


