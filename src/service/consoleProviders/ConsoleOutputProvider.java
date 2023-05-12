package src.service.consoleProviders;

import src.service.OutputProvider;

/**
 * Class Console-based implementation of OutputProvider.
 *
 * @author Sergio Ruy
 */
public class ConsoleOutputProvider implements OutputProvider {

    /**
     * Prints the specified message to the console.
     *
     * @param message to be printed.
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
