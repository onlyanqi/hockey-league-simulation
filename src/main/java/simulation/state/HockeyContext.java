package simulation.state;

import config.AppConfig;
import org.json.simple.JSONObject;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import simulation.factory.ValidationConcrete;
import simulation.model.User;
import validator.IValidation;

public class HockeyContext {

    private IHockeyState hockeyState;
    private User user;
    private IValidation iValidation;

    public HockeyContext() {
    }

    public HockeyContext(User user) {
        this.user = user;
        ValidationConcrete validationConcrete = new ValidationConcrete();
        iValidation = validationConcrete.newValidation();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void startAction(JSONObject jsonFromInput) throws Exception {
        if (iValidation.isNotNull(jsonFromInput)) {
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
        } while (iValidation.isNotNull(hockeyState));

    }

    public IHockeyState getHockeyState() {
        return hockeyState;
    }
}
