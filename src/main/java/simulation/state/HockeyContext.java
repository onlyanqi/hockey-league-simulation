package simulation.state;

import config.AppConfig;
import org.json.simple.JSONObject;
import persistance.dao.IDaoFactory;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import simulation.model.IModelFactory;
import simulation.model.IUser;

public class HockeyContext implements IHockeyContext {

    private static IHockeyContext hockeyContext;
    private IHockeyState hockeyState;
    private IUser user;
    private IModelFactory modelFactory;
    private IDaoFactory daoFactory;

    private HockeyContext() {
    }

    public static IHockeyContext getInstance() {
        if (null == hockeyContext) {
            hockeyContext = new HockeyContext();
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

    @Override
    public IModelFactory getModelFactory() {
        return this.modelFactory;
    }

    @Override
    public void setModelFactory(IModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public IDaoFactory getDaoFactory() {
        return daoFactory;
    }

    public void setDaoFactory(IDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
