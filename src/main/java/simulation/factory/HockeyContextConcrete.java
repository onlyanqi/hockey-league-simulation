package simulation.factory;

import simulation.dao.DaoFactory;
import simulation.dao.IDaoFactory;
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
        IDaoFactory daoFactory = DaoFactory.getInstance();

        hockeyContext.setModelFactory(modelFactory);
        hockeyContext.setDaoFactory(daoFactory);

        return hockeyContext;
    }

}
