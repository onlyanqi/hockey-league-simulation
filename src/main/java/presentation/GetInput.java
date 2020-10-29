package presentation;

import java.util.Scanner;

public class GetInput {

    public static String getUserInput(String input) {

        Scanner scanner = new Scanner(System.in);
        ConsoleOutput.printToConsole(input);
        return scanner.nextLine();

    }

}
