package simulation.factory;

import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

public class HockeyContextConcrete implements IHockeyContextFactory{

    public IHockeyContext newHockeyContext() {
        return createHockeyContext();
    }

    private IHockeyContext createHockeyContext(){
        IHockeyContext hockeyContext = HockeyContext.getInstance();

        IAgingFactory agingFactory = new AgingConcrete();
        hockeyContext.setAgingFactory(agingFactory);



        return hockeyContext;
    }

}
