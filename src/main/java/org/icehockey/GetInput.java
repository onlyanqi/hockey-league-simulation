package org.icehockey;

import java.util.Scanner;

public class GetInput {

    public static String getUserInput(String input){

        Scanner scanner = new Scanner(System.in);
        System.out.println(input);
        return scanner.nextLine();

    }

}
