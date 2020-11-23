package simulation.factory;

import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

public class HockeyContextConcrete implements IHockeyContextFactory{

    private static IHockeyContextFactory hockeyContextConcrete;

    private HockeyContextConcrete(){}

    public static IHockeyContextFactory getInstance(){
        if(hockeyContextConcrete == null){
            hockeyContextConcrete = new HockeyContextConcrete();
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

        IPlayerFactory playerFactory = new PlayerConcrete();
        hockeyContext.setPlayerFactory(playerFactory);

        ITeamFactory teamFactory = new TeamConcrete();
        hockeyContext.setTeamFactory(teamFactory);

        IManagerFactory managerFactory = new ManagerConcrete();
        hockeyContext.setManagerFactory(managerFactory);

        IConferenceFactory conferenceFactory = new ConferenceConcrete();
        hockeyContext.setConferenceFactory(conferenceFactory);

        IInjuryFactory injuryFactory = new InjuryConcrete();
        hockeyContext.setInjuryFactory(injuryFactory);

        IGamePlayConfigFactory gamePlayConfigFactory = new GamePlayConfigConcrete();
        hockeyContext.setGamePlayConfigFactory(gamePlayConfigFactory);

        ITradingFactory tradingFactory = new TradingConcrete();
        hockeyContext.setTradingFactory(tradingFactory);

        IUserFactory userFactory = new UserConcrete();
        hockeyContext.setUserFactory(userFactory);

        ITradeOfferFactory tradeOfferFactory = new TradeOfferConcrete();
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

        ILeagueFactory leagueFactory = new LeagueConcrete();
        hockeyContext.setLeagueFactory(leagueFactory);

        IDivisionFactory divisionFactory = new DivisionConcrete();
        hockeyContext.setDivisionFactory(divisionFactory);

        return hockeyContext;
    }

}
