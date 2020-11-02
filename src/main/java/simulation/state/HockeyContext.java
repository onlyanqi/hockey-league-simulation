package simulation.state;

import config.AppConfig;
import org.json.simple.JSONObject;
import simulation.model.User;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;

public class HockeyContext {

    private IHockeyState hockeyState;
    private User user;

    public HockeyContext() {
    }

    public HockeyContext(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void startAction(JSONObject jsonFromInput) throws Exception {

        if (jsonFromInput != null) {
            hockeyState = new ImportState(this, jsonFromInput);
            hockeyState.entry();
            hockeyState.process();
            IUserInputForTeamCreation inputForTeamCreation = AppConfig.getInstance().getInputForTeamCreation();
            IConsoleOutputForTeamCreation outputForTeamCreation = AppConfig.getInstance().getOutputForTeamCreation();
            hockeyState = new CreateTeamState(this,
                    inputForTeamCreation, outputForTeamCreation);
        } else {
            hockeyState = new LoadTeamState(this);
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        }

        do {
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        } while (hockeyState != null);

    }

    public IHockeyState getHockeyState() {
        return hockeyState;
    }
}
