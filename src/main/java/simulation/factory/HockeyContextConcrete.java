package simulation.factory;

import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

public class HockeyContextConcrete implements IHockeyContextFactory{

    public IHockeyContext newHockeyContext() {
        return new HockeyContext();
    }

}
