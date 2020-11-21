package simulation;

import db.data.IUserFactory;
import org.json.simple.JSONObject;
import presentation.ConsoleOutput;
import presentation.ReadUserInput;
import simulation.factory.*;
import simulation.model.Aging;
import simulation.model.SharedAttributes;
import simulation.model.User;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import simulation.state.IHockeyContext;


public class App {

    private static Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {


        log.debug("DEBUG Message");
        log.info("INFO Message");
        log.warn("WARN Message");
        log.error("ERROR Message");
        log.fatal("Fatal Message");


        String filePath = "";
        JSONObject jsonFromInput = null;

        ReadUserInput readUserInput = ReadUserInput.getInstance();

        String userName = readUserInput.getInput("Please enter username");
        try {
            if (userName == null || userName.isEmpty()) {
                ConsoleOutput.getInstance().printMsgToConsole("User name is invalid. Exiting the App.");
            } else {
                UserConcrete userConcrete = new UserConcrete();
                User user = userConcrete.newUser();

                user.setName(userName);
                if (user.getId() == 0) {
                    String password = readUserInput.getInput("Please enter password to register yourself");

                    user.setPassword(password);
                }
                filePath = readUserInput.getInput("Please provide location of JSON file. If not please press ENTER");

                if (filePath == null || filePath.isEmpty()) {
                    ConsoleOutput.getInstance().printMsgToConsole("File path is invalid. Exiting the App.");
                    System.exit(1);
                } else {
                    if (JSONController.invalidJSON(filePath)) {
                        ConsoleOutput.getInstance().printMsgToConsole("Invalid JSON file Provided.Exiting the app!");
                        System.exit(1);
                    }
                    jsonFromInput = JSONController.readJSON(filePath);
                }
                IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
                IHockeyContext context = hockeyContextFactory.newHockeyContext();
                context.setUser(user);
                context.startAction(jsonFromInput);

            }
        } catch (FileNotFoundException e) {
            ConsoleOutput.getInstance().printMsgToConsole("File Not found. " + e);
        } catch (Exception e) {
            ConsoleOutput.getInstance().printMsgToConsole("System faced unexpected exception. Please contact team. " + e);
        }

    }
}