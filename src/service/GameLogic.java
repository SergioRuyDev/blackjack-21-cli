package src.service;

import src.model.Deck;
import src.model.Players;

import java.util.Scanner;

/**
 * Class for add all the logic of the game
 *
 * @author Sergio Ruy
 */

public class GameLogic {

    private Deck newDeck;
    private String playerName;
    private float balance;
    private float bet;
    private boolean youDone;
    private boolean dealerDone;
    private Players dealer;
    private Players you;
    private Scanner scanner = new Scanner(System.in);
    private boolean doubleDownAllowed;

    GameLogic(String pName) {

        this.balance = 100;
        this.newDeck = new Deck(4, true);
        boolean gameOver = false;
        this.playerName = pName;

        System.out.println("**************************************************************************************");
        System.out.println("* Congratulations!! " + this.playerName + ", you have got 100 complimentary chips for playing. Enjoy! *");
        System.out.println("**************************************************************************************");

        // Players init
        you = new Players(this.playerName);
        dealer = new Players("Dealer");

        // The game start here
        while (this.balance > 0 && !gameOver) {

            System.out.println("\n" + this.playerName + ", Do you want to DEAl or END the game [D or E]??");
            String gameInit = scanner.next();

            if (gameInit.compareToIgnoreCase("D") == 0) {

//                this.dealTheGame(); // TODO
            } else {

                gameOver = true;
            }
        }
    }
}
