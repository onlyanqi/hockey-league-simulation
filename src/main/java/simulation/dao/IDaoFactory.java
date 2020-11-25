package simulation.dao;

public interface IDaoFactory {

    IAgingDao newAgingDao();

    IConferenceDao newConferenceDao();

    IDivisionDao newDivisionDao();

    ILeagueDao newLeagueDao();

    IPlayerDao newPlayerDao();

    ITeamDao newTeamDao();

    ITradeOfferDao newTradeOfferDao();

    ITradingDao newTradingDao();

    IUserDao newUserDao();


}
