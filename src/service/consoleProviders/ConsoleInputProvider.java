package src.service.consoleProviders;

import src.service.InputProvider;

import java.util.Scanner;

/**
 * Class Console-based implementation of InputProvider.
 *
 * @author Sergio Ruy
 */
public class ConsoleInputProvider implements InputProvider {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Gets the next line of input from the console.
     *
     * @return The next line as a string.
     */
    @Override
    public String getNext() {
        return scanner.nextLine().trim();
    }

    /**
     * Gets the next float value of input from the console.
     *
     * @return The next float value.
     */
    @Override
    public float getNextFloat() {
        float value = scanner.nextFloat();
        scanner.nextLine();
        return value;
    }
}
