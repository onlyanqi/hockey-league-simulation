package presentation;

import java.util.Scanner;

public class ReadUserInput {

    private static ReadUserInput readUserInput;

    public static ReadUserInput getInstance(){
        if(null == readUserInput){
            readUserInput = new ReadUserInput();
        }
        return readUserInput;
    }

    public static String getUserInput(String input) {
        ConsoleOutput.printToConsole(input);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String getInput(String input){
        ConsoleOutput consoleOutput = ConsoleOutput.getInstance();
        consoleOutput.printMsgToConsole(input);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String getUserTradeResponse(){
        String userResponse;
        boolean isValid;
        userResponse = getInput("Enter \"A\" to accept or \"R\" to reject the trade offer.");
        do {
            if(userResponse == null || userResponse.equals("".trim())){
                userResponse = getInput("Kindly check the input. " +
                        "Enter \"A\" to accept or \"R\" to reject the trade offer.");
                isValid = true;
            } else if(userResponse.equalsIgnoreCase("A".trim()) && userResponse.equalsIgnoreCase("R".trim())){

                isValid = false;
            } else {
                userResponse = getInput("Kindly check the input. " +
                        "Enter \"A\" to accept or \"R\" to reject the trade offer.");
                isValid = true;
            }
        } while(isValid);

        return userResponse;
    }

}
