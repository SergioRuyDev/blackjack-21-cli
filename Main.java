import src.service.InputProvider;
import src.service.OutputProvider;
import src.service.consoleProviders.ConsoleInputProvider;
import src.service.consoleProviders.ConsoleOutputProvider;
import src.service.impl.GameLogicImpl;

public class Main {
    public static void main(String[] args) {

        // Create implementations of my InputProvider and OutputProvider interfaces
        InputProvider inputProvider = new ConsoleInputProvider();
        OutputProvider outputProvider = new ConsoleOutputProvider();

        outputProvider.print("\n\t\t\t\t***************************************");
        outputProvider.print("\t\t\t\t*                                     *");
        outputProvider.print("\t\t\t\t*       BLACKJACK by Sergio Ruy       *");
        outputProvider.print("\t\t\t\t*                                     *");
        outputProvider.print("\t\t\t\t***************************************\n");

        outputProvider.print("Enter Your Name:\n");
        String playerName = inputProvider.getNext();

        new GameLogicImpl(playerName, inputProvider, outputProvider).playGame();
    }
}
