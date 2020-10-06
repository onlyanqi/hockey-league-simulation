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

        boolean filePathProvided = false;

        if(args.length ==1 ){
            filePathProvided = true;
        }else if(args.length > 1){
            System.out.println("Please provide only one argument");
            filePathProvided = false;
        }

        HockeyContext context = new HockeyContext();
        context.startAction(filePathProvided);




    }
}
