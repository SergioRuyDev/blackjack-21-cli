package src.service.consoleProviders;

import src.service.OutputProvider;

public class ConsoleOutputProvider implements OutputProvider {

    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
