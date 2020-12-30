package simulation;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import presentation.ConsoleOutput;
import presentation.IReadUserInput;
import presentation.ReadUserInput;
import simulation.GameSubjectObservers.*;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.model.IModelFactory;
import simulation.model.IUser;
import simulation.state.IHockeyContext;
import simulation.trophyPublisherSubsribers.*;

import java.io.FileNotFoundException;


public class App {

    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {

        String filePath = "";
        JSONObject jsonFromInput = null;

        IReadUserInput readUserInput = ReadUserInput.getInstance();

        String userName = readUserInput.getInput("Please enter username");
        try {
            if (userName == null || userName.isEmpty()) {
                log.error("Invalid Username " + userName);
                ConsoleOutput.getInstance().printMsgToConsole("User name is invalid. Exiting the App.");
            } else {
                IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
                IHockeyContext context = hockeyContextFactory.newHockeyContext();

                IModelFactory userConcrete = context.getModelFactory();
                IUser user = userConcrete.createUser();

                user.setName(userName);
                filePath = readUserInput.getInput("Please provide location of JSON file. If not please press ENTER");

                if (filePath == null || filePath.isEmpty()) {
                    ConsoleOutput.getInstance().printMsgToConsole("Loading the team...");
                } else {
                    if (JSONController.invalidJSON(filePath)) {
                        log.error("Invalid JSON file " + filePath);
                        ConsoleOutput.getInstance().printMsgToConsole("Invalid JSON file Provided.Exiting the app!");
                        System.exit(1);
                    }
                    jsonFromInput = JSONController.readJSON(filePath);
                }

                addSubscribers();
                context.setUser(user);
                context.startAction(jsonFromInput);
            }
        } catch (FileNotFoundException e) {
            log.error("File " + filePath + " not found " + e);
            ConsoleOutput.getInstance().printMsgToConsole("File Not found. " + e);
        } catch (Exception e) {
            log.error("Exception occurred " + e);
            ConsoleOutput.getInstance().printMsgToConsole("System faced unexpected exception. Please contact team. " + e);
        }

    }

    private static void addSubscribers() {

        GoalSubject.getInstance().attach(new GoalObserver());
        PenaltySubject.getInstance().attach(new PenaltyObserver());
        SaveSubject.getInstance().attach(new SaveObserver());
        ShotSubject.getInstance().attach(new ShotObserver());
        TotalGamesSubject.getInstance().attach(new TotalGameObserver());
        TrophySystemPublisher.getInstance().subscribe("coachStatAbilityUpdate", new CoachStatAbilitySubscriber());
        TrophySystemPublisher.getInstance().subscribe("goalScoreUpdate", new GoalScoreSubscriber());
        TrophySystemPublisher.getInstance().subscribe("penaltyCountUpdate", new PenaltyCountSubscriber());
        TrophySystemPublisher.getInstance().subscribe("savesUpdate", new SavesSubscriber());
    }
}
