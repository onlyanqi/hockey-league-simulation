package simulation.dao;

public class DaoFactory implements IDaoFactory {

    private static IDaoFactory daoFactory;

    private DaoFactory() {
    }

    public static IDaoFactory getInstance() {
        if (null == daoFactory) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public IAgingDao newAgingDao() {
        return null;
    }

    public IConferenceDao newConferenceDao() {
        return null;
    }

    public IDivisionDao newDivisionDao() {
        return null;
    }

    public ILeagueDao newLeagueDao() {
        return null;
    }

    public IPlayerDao newPlayerDao() {
        return null;
    }

    public ITradeOfferDao newTradeOfferDao() {
        return null;
    }

    public ITeamDao newTeamDao() {
        return null;
    }

    public ITradingDao newTradingDao() {
        return null;
    }

    public IUserDao newUserDao() {
        return null;
    }

    @Override
    public IFreeAgentDao newFreeAgentDao() {
        return null;
    }
}
