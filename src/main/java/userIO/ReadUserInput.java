package userIO;

import simulation.factory.ConsoleOutputConcrete;

import java.util.Scanner;

public class GetInput implements IUserInput{

    public String getUserInput(String input) {
        Scanner scanner = new Scanner(System.in);
        ConsoleOutputConcrete consoleOutputConcrete = new ConsoleOutputConcrete();
        IConsoleOutput consoleOutput = consoleOutputConcrete.newConsoleOutput();
        consoleOutput.printToConsole(input);
        return scanner.nextLine();
    }

    @Override
    public String getInput(String input) {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
