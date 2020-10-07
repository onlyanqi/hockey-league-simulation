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

        filePath  = GetInput.getUserInput("Please provide location of JSON file. If not please press ENTER");

        HockeyContext context = new HockeyContext();
        context.startAction(filePath);

    }
}
