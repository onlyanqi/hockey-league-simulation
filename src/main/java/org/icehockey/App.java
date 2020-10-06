package org.icehockey;

import model.HockeyContext;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {


        System.out.println("Arguments are: "+ args.length);

        String filePath = "";

        if(args.length ==1 ){
            filePath = args[0];
        }else if(args.length > 1){
            System.out.println("Please provide only one argument");
            return;
        }

        HockeyContext context = new HockeyContext();
        context.startAction(filePath);




    }
}
