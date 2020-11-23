package simulation.state;

import org.json.simple.JSONObject;
import simulation.factory.*;
import simulation.model.IUser;


public interface IHockeyContext {

    IUser getUser();

    void setUser(IUser user);

    void startAction(JSONObject jsonFromInput) throws Exception;

    IHockeyState getHockeyState();

    void setAgingFactory(IAgingFactory agingFactory);

    IAgingFactory getAgingFactory();

    ICoachFactory getCoachFactory();

    void setCoachFactory(ICoachFactory coachFactory);

    void setFreeAgentFactory(IFreeAgentFactory freeAgentFactory);

    IFreeAgentFactory getFreeAgentFactory();

    void setPlayerFactory(IPlayerFactory playerFactory);

    IPlayerFactory getPlayerFactory();

    void setTeamFactory(ITeamFactory teamFactory);

    ITeamFactory getTeamFactory();

    void setManagerFactory(IManagerFactory managerFactory);

    IManagerFactory getManagerFactory();

    void setConferenceFactory(IConferenceFactory conferenceFactory);

    IConferenceFactory getConferenceFactory();

    void setInjuryFactory(IInjuryFactory injuryFactory);

    IInjuryFactory getInjuryFactory();

    void setTrainingFactory(ITrainingFactory trainingFactory);

    ITrainingFactory getTrainingFactory();

    void setGamePlayConfigFactory(IGamePlayConfigFactory gamePlayConfigFactory);

    IGamePlayConfigFactory getGamePlayConfigFactory();

    void setTradingFactory(ITradingFactory tradingFactory);

    ITradingFactory getTradingFactory();

    void setUserFactory(IUserFactory userFactory);

    IUserFactory getUserFactory();

    void setTradeOfferFactory(ITradeOfferFactory tradeOfferFactory);

    ITradeOfferFactory getTradeOfferFactory();

    void setGameFactory(IGameFactory gameFactory);

    IGameFactory getGameFactory();

    void setNHLEventsFactory(INHLEventsFactory nhlEventsFactory);

    INHLEventsFactory getNHLEventsFactory();

    void setGameScheduleFactory(IGameScheduleFactory gameScheduleFactory);

    IGameScheduleFactory getGameScheduleFactory();

    void setTeamStandingFactory(ITeamStandingFactory teamStandingFactory);

    ITeamStandingFactory getTeamStandingFactory();

    void setLeagueFactory(ILeagueFactory leagueFactory);

    ILeagueFactory getLeagueFactory();

    IDivisionFactory getDivisionFactory();

    void setDivisionFactory(IDivisionFactory divisionFactory);

}
