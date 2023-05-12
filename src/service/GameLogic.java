package src.service;

/**
 * Interface for add all method to the game logic
 *
 * @author Sergio Ruy
 */

public interface GameLogic {

    void dealTheGame();

    boolean checkIfBlackJack();

    void yourPlay();

    void hit();

    void stay();

    void doubleDown();

    void dealersPlay();

    void decideWinner();
}
