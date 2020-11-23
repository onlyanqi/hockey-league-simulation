package simulation.factory;

import simulation.mock.ConferenceConcreteMock;
import simulation.mock.DivisionConcreteMock;
import simulation.mock.PlayerConcreteMock;
import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

public class HockeyContextConcreteMock implements IHockeyContextFactory {

    private static IHockeyContextFactory hockeyContextConcrete;

    private HockeyContextConcreteMock(){}

    public static IHockeyContextFactory getInstance(){
        if(hockeyContextConcrete == null){
            return new HockeyContextConcreteMock();
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

        IFreeAgentFactory freeAgentFactory = new FreeAgentConcrete();
        hockeyContext.setFreeAgentFactory(freeAgentFactory);

        IPlayerFactory playerFactory = new PlayerConcreteMock();
        hockeyContext.setPlayerFactory(playerFactory);

        ITeamFactory teamFactory = new TeamConcreteMock();
        hockeyContext.setTeamFactory(teamFactory);

        IManagerFactory managerFactory = new ManagerConcrete();
        hockeyContext.setManagerFactory(managerFactory);

        IConferenceFactory conferenceFactory = new ConferenceConcreteMock();
        hockeyContext.setConferenceFactory(conferenceFactory);

        IInjuryFactory injuryFactory = new InjuryConcrete();
        hockeyContext.setInjuryFactory(injuryFactory);

        IGamePlayConfigFactory gamePlayConfigFactory = new GamePlayConfigConcrete();
        hockeyContext.setGamePlayConfigFactory(gamePlayConfigFactory);

        ITradingFactory tradingFactory = new TradingConcreteMock();
        hockeyContext.setTradingFactory(tradingFactory);

        IUserFactory userFactory = new UserConcrete();
        hockeyContext.setUserFactory(userFactory);

        ITradeOfferFactory tradeOfferFactory = new TradeOfferConcreteMock();
        hockeyContext.setTradeOfferFactory(tradeOfferFactory);

        IGameFactory gameFactory = new GameConcrete();
        hockeyContext.setGameFactory(gameFactory);

        INHLEventsFactory inhlEventsFactory = new NHLEventsConcrete();
        hockeyContext.setNHLEventsFactory(inhlEventsFactory);

        IGameScheduleFactory gameScheduleFactory = new GameScheduleConcrete();
        hockeyContext.setGameScheduleFactory(gameScheduleFactory);

        ITeamStandingFactory teamStandingFactory = new TeamStandingConcrete();
        hockeyContext.setTeamStandingFactory(teamStandingFactory);

        ITrainingFactory trainingFactory = new TrainingConcrete();
        hockeyContext.setTrainingFactory(trainingFactory);

        ILeagueFactory leagueFactory = new LeagueConcreteMock();
        hockeyContext.setLeagueFactory(leagueFactory);

        IDivisionFactory divisionFactory = new DivisionConcreteMock();
        hockeyContext.setDivisionFactory(divisionFactory);

        return hockeyContext;
    }

}
