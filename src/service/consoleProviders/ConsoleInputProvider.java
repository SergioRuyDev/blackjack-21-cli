package src.service.consoleProviders;

import src.service.InputProvider;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getNext() {
        return scanner.nextLine().trim();
    }

    @Override
    public float getNextFloat() {
        float value = scanner.nextFloat();
        scanner.nextLine();
        return value;
    }
}
