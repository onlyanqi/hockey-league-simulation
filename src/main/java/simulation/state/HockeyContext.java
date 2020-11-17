package simulation.state;

import config.AppConfig;
import org.json.simple.JSONObject;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import simulation.factory.IAgingFactory;
import simulation.model.User;

public class HockeyContext implements IHockeyContext{

    private IHockeyState hockeyState;
    private User user;
    private IAgingFactory agingFactory;

    public HockeyContext() {
    }

    public HockeyContext(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
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

    @Override
    public void setAgingFactory(IAgingFactory agingFactory) {
        this.agingFactory = agingFactory;
    }

    @Override
    public IAgingFactory getAgingFactory(){
        return this.agingFactory;
    }

}
