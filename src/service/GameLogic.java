package src.service;

/**
 * Interface for add all method to the game logic
 *
 * @author Sergio Ruy
 */

public interface GameLogic {

    public void dealTheGame();

    public boolean checkIfBlackJack();

    public void yourPlay();

    public void hit();

    public void stay();

    public void doubleDown();

    public void dealersPlay();

    public void decideWinner();
}
