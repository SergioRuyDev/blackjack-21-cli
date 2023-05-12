package src.service.impl;

import src.model.Deck;
import src.model.Players;
import src.service.GameLogic;
import src.service.InputProvider;
import src.service.OutputProvider;

/**
 * Class provides the main logic for the CLI blackjack game
 * It implements the GameLogic interface and initiates a game
 *
 * @author Sergio Ruy
 */

public class GameLogicImpl implements GameLogic {

    private Deck newDeck;
    private String playerName;
    private float balance;
    private float bet;
    private boolean youDone;
    private boolean dealerDone;
    private Players dealer;
    private Players you;
    private InputProvider inputProvider;
    private OutputProvider outputProvider;
    private boolean doubleDownAllowed;


    /**
     * Constructor a new instance of the game with a player, deck, and game balance..
     * @param pName player's name.
     * @param inputProvider provider for user input.
     * @param outputProvider provider for game output.
     */
    public GameLogicImpl(String pName, InputProvider inputProvider, OutputProvider outputProvider) {
        this.inputProvider = inputProvider;
        this.outputProvider = outputProvider;
        this.balance = 100;
        this.newDeck = new Deck(4, true);
        this.playerName = pName;

        outputProvider.print("**************************************************************************************");
        outputProvider.print("* Congratulations!! " + this.playerName + ", you have got 100 complimentary chips for playing. Enjoy! *");
        outputProvider.print("**************************************************************************************");

        // Players init
        you = new Players(this.playerName);
        dealer = new Players("Dealer");
    }

    /**
     * Handles the game, dealing and ending or restarting the game.
     */
    public void playGame() {
        boolean gameOver = false;
        while (this.balance > 0 && !gameOver) {
            outputProvider.print("\n" + this.playerName + ", Do you want to DEAl or END the game [D or E]??");
            String gameInit = inputProvider.getNext();

            if (gameInit != null && gameInit.compareToIgnoreCase("D") == 0) {
                this.dealTheGame();
            } else {
                gameOver = true;
            }
        }
        outputProvider.print("\n" + this.playerName + ", Game Ended!!!");

        // Play Again
        outputProvider.print("\n" + this.playerName + ", Do you want to play again [Y or N]");
        String Y = inputProvider.getNext();
        if (Y != null && Y.compareToIgnoreCase("Y") == 0) {
            new GameLogicImpl(this.playerName, this.inputProvider, this.outputProvider).playGame();
        }
    }

    /**
     * Method for deal the cards and manages the game.
     * This includes prompting the user for their bet, dealing the initial cards, and managing the turns of the game.
     */
    @Override
    public void dealTheGame() {
        boolean blackjack = false;
        this.bet = 0;
        this.doubleDownAllowed = true;

        outputProvider.print(String.format("\nBalance:$%.1f\n", this.balance));
        String msg = "Enter your bet for this game:";

        while(this.bet <= 0) {

            try {

                outputProvider.print("\n" + msg);
                this.bet = inputProvider.getNextFloat();
            } catch(NumberFormatException e){

                outputProvider.print("Invalid input, please enter a natural number for your bet.");
            } finally {

                msg = "Enter your bet in Integers (natural numbers) please: ";
            }
        }


        if((this.bet >= 1) && (this.bet % 1 == 0) && (this.balance - this.bet >= 0)){

            this.balance = this.balance - this.bet;

            // players start with empty hands
            you.emptyHand();
            dealer.emptyHand();

            this.youDone = false;
            this.dealerDone = false;

            // Distributing initial cards to players
            you.addCardToPlayersHand(newDeck.dealingNextCard());
            dealer.addCardToPlayersHand(newDeck.dealingNextCard());
            you.addCardToPlayersHand(newDeck.dealingNextCard());
            dealer.addCardToPlayersHand(newDeck.dealingNextCard());


            // Cards Dealt
            outputProvider.print("\n********** CARDS DEALT **********\n");
            dealer.printCardsInHand(false);
            you.printCardsInHand(true);

            outputProvider.print(String.format("Your Score:%d\t", you.getPlayersHandTotal()));
            outputProvider.print(String.format("Bet:$%.0f\t", this.bet));
            outputProvider.print(String.format("Balance:$%.1f\n\n", this.balance));

            // checking state on initial card -- if BlackJack
            blackjack = this.checkIfBlackJack();

            while(!this.youDone || !this.dealerDone) {

                if(!this.youDone) {

                    this.yourPlay();

                } else if(!this.dealerDone) {

                    this.dealersPlay();
                }

                outputProvider.print("");
            }

            if(!blackjack){

                this.decideWinner();
            }
        } else {

            outputProvider.print("\nYour bet amount is wrong, it should be a natural number and should not exceed your balance");
            outputProvider.print(String.format("Your Balance:$%.1f\n\n", this.balance));
        }
    }

    /**
     * Check if player has blackjack and return boolean
     * @return true if the player has blackjack and the dealer does not, false.
     */
    @Override
    public boolean checkIfBlackJack() {
        boolean blackJack = false;

        if(you.getPlayersHandTotal() == 21) {
            this.youDone = true;
            this.dealerDone = true;

            if(you.getPlayersHandTotal() > dealer.getPlayersHandTotal() || dealer.getPlayersHandTotal() > 21){

                outputProvider.print("\t\t\t\t*********************************\n" +
                        "\t\t\t\t*                               *\n" +
                        "\t\t\t\t* UHUUUL!!...BLACKJACK, YOU WON *\n" +
                        "\t\t\t\t*                               *\n" +
                        "\t\t\t\t*********************************\n");

                dealer.printCardsInHand(true);

                outputProvider.print(String.format("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()));
                outputProvider.print(String.format("Your Bet was :$%.0f\t", this.bet));
                outputProvider.print(String.format("Your Balance was:$%.1f\n", this.balance));
                outputProvider.print(String.format("You win[3:2]:$%.1f\t", (3 * this.bet) / 2));

                this.balance = this.balance + (3 * this.bet) / 2 + this.bet;
                outputProvider.print(String.format("Your Current Balance:$%0.1f\n", this.balance));

                blackJack = true;
            } else {

                outputProvider.print("\tIt could have been a BlackJack for you...\n");
                dealer.printCardsInHand(true);

                outputProvider.print(String.format("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()));
                blackJack = false;
            }
        } else if(dealer.getPlayersHandTotal() == 21) {

            dealer.printCardsInHand(true);
            outputProvider.print(String.format("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()));

            outputProvider.print("\t\t\t\t*********************************\n" +
                    "\t\t\t\t*                               *\n" +
                    "\t\t\t\t* DEALER's BLACKJACK, YOU LOST  *\n" +
                    "\t\t\t\t*                               *\n" +
                    "\t\t\t\t*********************************\n");

            this.dealerDone = true;
            blackJack = false;
        }

        return blackJack;
    }

    /**
     * Method for handle the players turn and actions
     * Flags - Hit, Stand, Double if they have enough balance
     */
    @Override
    public void yourPlay() {
        String answer;

        if(this.balance >= this.bet && this.doubleDownAllowed) {
            outputProvider.print("Hit or Stay or Double Down? [Enter H or S or DD]");
        } else {
            this.doubleDownAllowed = false;
            outputProvider.print("Hit or Stay? [Enter H or S(or press any letter to Stay)]");
        }

        answer = inputProvider.getNext();
        outputProvider.print("You entered: " + answer);

        if(answer.compareToIgnoreCase("H") == 0) {
            this.hit();
            this.doubleDownAllowed = false;
        } else if(answer.compareToIgnoreCase("DD") == 0 && this.doubleDownAllowed) {
            this.doubleDown();
        } else if(answer.compareToIgnoreCase("SS") == 0) {
            this.doubleDownAllowed = false;
        } else {
            this.stay();
        }
    }

    /**
     * Method for add card to the player hand
     * If the player's hand total exceeds 21 after hitting, the player busts and the turn ends.
     */
    @Override
    public void hit() {
        outputProvider.print("\tYou Choose to Hit.\n");
        youDone = !you.addCardToPlayersHand(newDeck.dealingNextCard());
        you.printCardsInHand(true);
        outputProvider.print(String.format("Your Score:%d\t", you.getPlayersHandTotal()));
        outputProvider.print(String.format("Bet:$%.0f\t", this.bet));
        outputProvider.print(String.format("Balance:$%.1f\n\n", this.balance));

        if(you.getPlayersHandTotal() > 21) {
            outputProvider.print("\t\t\t\t**************");
            outputProvider.print("\t\t\t\t*            *");
            outputProvider.print("\t\t\t\t* YOU BUSTED *");
            outputProvider.print("\t\t\t\t*            *");
            outputProvider.print("\t\t\t\t**************\n");
            dealer.printCardsInHand(true);
            outputProvider.print(String.format("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()));
            youDone = true;
            dealerDone = true;
        }
    }

    /**
     * Method for ended the player turn
     * After the player stands, it's the dealer's turn.
     */
    @Override
    public void stay() {
        outputProvider.print("\tYou Choose to Stay, Dealer's turn \n");
        youDone = true;
    }

    /**
     * Method for double bet and add card to their hand
     * If the player's hand total exceeds 21 after doubling down, the player busts and the turn ends.
     */
    @Override
    public void doubleDown() {
        outputProvider.print("\tYou Choose to Double Down.\n");

        youDone = you.addCardToPlayersHand(newDeck.dealingNextCard());
        this.balance = this.balance - this.bet;
        this.bet = 2 * this.bet;
        youDone = true;
        you.printCardsInHand(true);

        outputProvider.print(String.format("Your Score:%d\t", you.getPlayersHandTotal()));
        outputProvider.print(String.format("Bet:$%.0f\t", this.bet));
        outputProvider.print(String.format("Balance:$%.1f\n\n", this.balance));

        if(you.getPlayersHandTotal() > 21){
            outputProvider.print("\t\t\t\t**************");
            outputProvider.print("\t\t\t\t*            *");
            outputProvider.print("\t\t\t\t* YOU BUSTED *");
            outputProvider.print("\t\t\t\t*            *");
            outputProvider.print("\t\t\t\t**************\n");
            dealer.printCardsInHand(true);
            outputProvider.print(String.format("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()));
            youDone = true;
            dealerDone = true;
        }

        outputProvider.print("Now , Dealer's turn \n");
    }

    /**
     * Method for control dealer actions
     * The dealer will hit if total is less than 17, otherwise they will stand.
     * If the dealer's hand total exceeds 21, the dealer busts and the turn ends.
     *
     */
    @Override
    public void dealersPlay() {
        if(dealer.getPlayersHandTotal() < 17){

            dealer.printCardsInHand(true);
            outputProvider.print(String.format("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()));
            outputProvider.print("\tDealer Hits \n");
            dealerDone = !dealer.addCardToPlayersHand(newDeck.dealingNextCard());

            if(dealer.getPlayersHandTotal() > 21){

                dealer.printCardsInHand(true);
                outputProvider.print(String.format("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()));
                outputProvider.print("\t\t\t\t*****************");
                outputProvider.print("\t\t\t\t*               *");
                outputProvider.print("\t\t\t\t* DEALER BUSTED *");
                outputProvider.print("\t\t\t\t*               *");
                outputProvider.print("\t\t\t\t*****************\n");
                dealerDone = true;
            }
        } else {

            dealer.printCardsInHand(true);
            outputProvider.print(String.format("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()));
            outputProvider.print("\tDealer Stays \n");
            dealerDone = true;
        }
    }

    /**
     * Method for determines the winner
     * Determined based on the totals of the player's and dealer's hands.
     *
     */
    @Override
    public void decideWinner() {
        int youSum = you.getPlayersHandTotal();
        int dealerSum = dealer.getPlayersHandTotal();

        if(youSum > dealerSum && youSum <= 21 || dealerSum > 21){

            outputProvider.print("\t\t\t\t************");
            outputProvider.print("\t\t\t\t*          *");
            outputProvider.print("\t\t\t\t* YOU WON  *");
            outputProvider.print("\t\t\t\t*          *");
            outputProvider.print("\t\t\t\t************\n");
            outputProvider.print(String.format("Your Bet was :$%.0f\t", this.bet));
            outputProvider.print(String.format("Your Balance was:$%.1f\n", this.balance));
            outputProvider.print(String.format("You win[1:1]:$%.0f\t", this.bet));

            this.balance = this.balance + this.bet + this.bet;
            outputProvider.print(String.format("Your Current Balance:$%.1f\n", balance));

        } else if(youSum == dealerSum){

            outputProvider.print("\t\t\t\t************");
            outputProvider.print("\t\t\t\t*          *");
            outputProvider.print("\t\t\t\t*   PUSH   *");
            outputProvider.print("\t\t\t\t*          *");
            outputProvider.print("\t\t\t\t************\n");
            this.balance = this.balance + this.bet;
            outputProvider.print(String.format("Your Current Balance:$%.1f\n", this.balance));
        } else {

            outputProvider.print("\t\t\t\t************");
            outputProvider.print("\t\t\t\t*          *");
            outputProvider.print("\t\t\t\t* YOU LOST *");
            outputProvider.print("\t\t\t\t*          *");
            outputProvider.print("\t\t\t\t############\n");
            outputProvider.print(String.format("You lose[1:1]: $%.0f!!\n", this.bet));
            outputProvider.print(String.format("Your Current Balance:$%.1f\n", this.balance));
        }
    }
}
