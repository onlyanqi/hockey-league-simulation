package simulation.state;

import config.AppConfig;
import org.json.simple.JSONObject;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import simulation.factory.*;
import simulation.model.IUser;
import simulation.model.User;

public class HockeyContext implements IHockeyContext{

    private IHockeyState hockeyState;
    private IUser user;
    private static IHockeyContext hockeyContext;

    private HockeyContext() {
    }

    public static IHockeyContext getInstance(){
        if(null == hockeyContext){
            return new HockeyContext();
        }
        return hockeyContext;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public void setUser(IUser user) {
        this.user = user;
    }

    @Override
    public void startAction(JSONObject jsonFromInput) throws Exception {
        if (jsonFromInput == null || jsonFromInput.isEmpty()) {
            hockeyState = new LoadTeamState(this);
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        } else {
            hockeyState = new ImportState(this, jsonFromInput);
            hockeyState.entry();
            hockeyState.process();
            IUserInputForTeamCreation inputForTeamCreation = AppConfig.getInstance().getInputForTeamCreation();
            IConsoleOutputForTeamCreation outputForTeamCreation = AppConfig.getInstance().getOutputForTeamCreation();
            hockeyState = new CreateTeamState(this,
                    inputForTeamCreation, outputForTeamCreation);
        }

        do {
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        } while (hockeyState instanceof ISimulateState || hockeyState instanceof IHockeyState);

    }

    @Override
    public IHockeyState getHockeyState() {
        return hockeyState;
    }

}
