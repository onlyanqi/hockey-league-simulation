package simulation.factory;

import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

public class HockeyContextConcrete implements IHockeyContextFactory{

    private static IHockeyContextFactory hockeyContextConcrete;

    private HockeyContextConcrete(){}

    public static IHockeyContextFactory getInstance(){
        if(hockeyContextConcrete == null){
            return new HockeyContextConcrete();
        }
        return hockeyContextConcrete;
    }

    public IHockeyContext newHockeyContext() {
        return createHockeyContext();
    }

    private IHockeyContext createHockeyContext(){
        IHockeyContext hockeyContext = HockeyContext.getInstance();

        IAgingFactory agingFactory = new AgingConcrete();
        hockeyContext.setAgingFactory(agingFactory);

        ICoachFactory coachFactory = new CoachConcrete();
        hockeyContext.setCoachFactory(coachFactory);

        return hockeyContext;
    }

}
