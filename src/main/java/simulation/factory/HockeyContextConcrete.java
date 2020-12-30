package simulation.factory;

import simulation.model.IModelFactory;
import simulation.model.ModelFactory;
import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

public class HockeyContextConcrete implements IHockeyContextFactory {

    private static IHockeyContextFactory hockeyContextConcrete;

    private HockeyContextConcrete() {
    }

    public static IHockeyContextFactory getInstance() {
        if (hockeyContextConcrete == null) {
            hockeyContextConcrete = new HockeyContextConcrete();
        }
        return hockeyContextConcrete;
    }

    public IHockeyContext newHockeyContext() {
        return createHockeyContext();
    }

    private IHockeyContext createHockeyContext() {
        IHockeyContext hockeyContext = HockeyContext.getInstance();
        IModelFactory modelFactory = ModelFactory.getInstance();
        hockeyContext.setModelFactory(modelFactory);
        return hockeyContext;
    }

}
