package org.icehockey;

import dao.connect.DBConnection;
import model.HockeyContext;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {


        String filePath = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please provide location of JSON file. If not please press ENTER");
        filePath  = scanner.nextLine();

        HockeyContext context = new HockeyContext();
        context.startAction(filePath);





    }
}
