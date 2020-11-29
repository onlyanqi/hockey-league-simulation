package simulation.dao;

import simulation.mock.*;

public class DaoFactoryMock implements IDaoFactory{

    private static DaoFactoryMock daoFactoryMock;

    private DaoFactoryMock(){}

    public static DaoFactoryMock getInstance(){
        if(null == daoFactoryMock){
            daoFactoryMock = new DaoFactoryMock();
        }
        return daoFactoryMock;
    }

    @Override
    public IAgingDao newAgingDao() {
        return new AgingMock();
    }

    @Override
    public IConferenceDao newConferenceDao() {
        return new ConferenceMock();
    }

    @Override
    public IDivisionDao newDivisionDao() {
        return new DivisionMock();
    }

    public ILeagueDao newLeagueDao(){
        return new LeagueMock();
    }

    @Override
    public IPlayerDao newPlayerDao() {
        return new PlayerMock();
    }

    public ITeamDao newTeamDao(){
        return new TeamMock();
    }

    public ITradingDao newTradingDao(){
        return new TradingMock();
    }

    public ITradeOfferDao newTradeOfferDao(){
        return new TradeOfferMock();
    }

    public IUserDao newUserDao(){
        return new UserMock();
    }

    public IFreeAgentDao newFreeAgentDao(){
        return new FreeAgentMock();
    }
}
