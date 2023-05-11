import src.service.GameLogicImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\t\t\t\t***************************************");
        System.out.println("\t\t\t\t*                                     *");
        System.out.println("\t\t\t\t*       BLACKJACK by Sergio Ruy       *");
        System.out.println("\t\t\t\t*                                     *");
        System.out.println("\t\t\t\t***************************************\n");

        System.out.println("Enter Your Name:\n");
        String playerName = scanner.nextLine();

        new GameLogicImpl(playerName);

        scanner.close();
    }
}
