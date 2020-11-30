package persistance.dao;

public interface IDaoFactory {

    IAgingDao createAgingDao();

    IConferenceDao createConferenceDao();

    IDivisionDao createDivisionDao();

    ILeagueDao createLeagueDao();

    IPlayerDao createPlayerDao();

    ITeamDao createTeamDao();

    ITradeOfferDao createTradeOfferDao();

    ITradingDao createTradingDao();

    IUserDao createUserDao();

    IFreeAgentDao createFreeAgentDao();

    ISeasonDao createSeasonDao();
}
