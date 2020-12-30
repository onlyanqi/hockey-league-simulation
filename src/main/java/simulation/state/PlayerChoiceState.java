package simulation.state;

import config.AppConfig;
import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import presentation.ReadUserInput;
import simulation.model.Player;

public class PlayerChoiceState implements IHockeyState {

    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String IMPORTSTATE = "importState";
    private static final String CREATEORLOADTEAM = "createOrLoadTeam";
    private static final Logger log = Logger.getLogger(Player.class);
    private final String input;
    private final String stateName;
    private final IHockeyContext hockeyContext;
    private String userInput;

    public PlayerChoiceState(IHockeyContext hockeyContext, String input, String stateName) {
        this.input = input;
        this.stateName = stateName;
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        ConsoleOutput.getInstance().printMsgToConsole(input);
    }

    @Override
    public void process() {
        userInput = ReadUserInput.getInstance().getInput("");
    }

    @Override
    public IHockeyState exit() {
        switch (stateName) {
            case IMPORTSTATE: {
                if (userInput.equals(ONE)) {
                    IUserInputForTeamCreation inputForTeamCreation = AppConfig.getInstance().getInputForTeamCreation();
                    IConsoleOutputForTeamCreation outputForTeamCreation = AppConfig.getInstance().getOutputForTeamCreation();
                    CreateTeamState createTeamState = new CreateTeamState(hockeyContext,
                            inputForTeamCreation, outputForTeamCreation);
                    return createTeamState;
                } else if (userInput.equals(TWO)) {
                    LoadTeamState loadTeamState = new LoadTeamState(hockeyContext);
                    return loadTeamState;
                }
                break;
            }
            case CREATEORLOADTEAM: {
                SeasonSimulationState seasonSimulationState = new SeasonSimulationState(hockeyContext, Integer.parseInt(userInput));
                return seasonSimulationState;
            }
            default: {
                log.error("Given State is invalid. Cannot proceed to simulating seasons");
                throw new IllegalStateException("Given State is invalid. Cannot proceed to simulating seasons");
            }
        }
        return null;
    }

}
