package userIO;

import java.util.Scanner;

public class ReadUserInput {

    public static String getUserInput(String input) {
        ConsoleOutput.printToConsole(input);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
