package simulation.state;

import config.AppConfig;
import org.json.simple.JSONObject;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import simulation.factory.*;
import simulation.model.IUser;
import simulation.model.User;

public class HockeyContext implements IHockeyContext{

    private IHockeyState hockeyState;
    private IUser user;
    private IAgingFactory agingFactory;
    private static IHockeyContext hockeyContext;
    private ICoachFactory coachFactory;
    private IFreeAgentFactory freeAgentFactory;
    private IPlayerFactory playerFactory;
    private ITeamFactory teamFactory;
    private IManagerFactory managerFactory;
    private IConferenceFactory conferenceFactory;
    private IInjuryFactory injuryFactory;
    private ITrainingFactory trainingFactory;
    private IGamePlayConfigFactory gamePlayConfigFactory;
    private ITradingFactory tradingFactory;
    private IUserFactory userFactory;
    private ITradeOfferFactory tradeOfferFactory;
    private IGameFactory gameFactory;
    private INHLEventsFactory inhlEventsFactory;
    private IGameScheduleFactory gameScheduleFactory;
    private ITeamStandingFactory teamStandingFactory;
    private ILeagueFactory leagueFactory;
    private IDivisionFactory divisionFactory;

    private HockeyContext() {
    }

    public static IHockeyContext getInstance(){
        if(null == hockeyContext){
            return new HockeyContext();
        }
        return hockeyContext;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public void setUser(IUser user) {
        this.user = user;
    }

    @Override
    public void startAction(JSONObject jsonFromInput) throws Exception {
        if (jsonFromInput == null || jsonFromInput.isEmpty()) {
            hockeyState = new LoadTeamState(this);
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        } else {
            hockeyState = new ImportState(this, jsonFromInput);
            hockeyState.entry();
            hockeyState.process();
            IUserInputForTeamCreation inputForTeamCreation = AppConfig.getInstance().getInputForTeamCreation();
            IConsoleOutputForTeamCreation outputForTeamCreation = AppConfig.getInstance().getOutputForTeamCreation();
            hockeyState = new CreateTeamState(this,
                    inputForTeamCreation, outputForTeamCreation);
        }

        do {
            hockeyState.entry();
            hockeyState.process();
            hockeyState = hockeyState.exit();
        } while (hockeyState instanceof ISimulateState || hockeyState instanceof IHockeyState);

    }

    @Override
    public IHockeyState getHockeyState() {
        return hockeyState;
    }

    @Override
    public void setAgingFactory(IAgingFactory agingFactory) {
        this.agingFactory = agingFactory;
    }

    @Override
    public IAgingFactory getAgingFactory(){
        return this.agingFactory;
    }

    @Override
    public ICoachFactory getCoachFactory() {
        return coachFactory;
    }

    @Override
    public void setCoachFactory(ICoachFactory coachFactory) {
        this.coachFactory = coachFactory;
    }

    @Override
    public IFreeAgentFactory getFreeAgentFactory() {
        return freeAgentFactory;
    }

    @Override
    public void setFreeAgentFactory(IFreeAgentFactory freeAgentFactory) {
        this.freeAgentFactory = freeAgentFactory;
    }

    @Override
    public IPlayerFactory getPlayerFactory() {
        return playerFactory;
    }

    @Override
    public void setPlayerFactory(IPlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
    }

    @Override
    public ITeamFactory getTeamFactory() {
        return teamFactory;
    }

    @Override
    public void setTeamFactory(ITeamFactory teamFactory) {
        this.teamFactory = teamFactory;
    }

    @Override
    public IManagerFactory getManagerFactory() {
        return managerFactory;
    }

    @Override
    public void setManagerFactory(IManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public IConferenceFactory getConferenceFactory() {
        return conferenceFactory;
    }

    @Override
    public void setConferenceFactory(IConferenceFactory conferenceFactory) {
        this.conferenceFactory = conferenceFactory;
    }

    @Override
    public IInjuryFactory getInjuryFactory() {
        return injuryFactory;
    }

    @Override
    public void setInjuryFactory(IInjuryFactory injuryFactory) {
        this.injuryFactory = injuryFactory;
    }

    @Override
    public ITrainingFactory getTrainingFactory() {
        return trainingFactory;
    }

    @Override
    public void setTrainingFactory(ITrainingFactory trainingFactory) {
        this.trainingFactory = trainingFactory;
    }

    @Override
    public IGamePlayConfigFactory getGamePlayConfigFactory() {
        return gamePlayConfigFactory;
    }

    @Override
    public void setGamePlayConfigFactory(IGamePlayConfigFactory gamePlayConfigFactory) {
        this.gamePlayConfigFactory = gamePlayConfigFactory;
    }

    @Override
    public ITradingFactory getTradingFactory() {
        return tradingFactory;
    }

    @Override
    public void setTradingFactory(ITradingFactory tradingFactory) {
        this.tradingFactory = tradingFactory;
    }

    @Override
    public IUserFactory getUserFactory() {
        return userFactory;
    }

    @Override
    public void setUserFactory(IUserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public ITradeOfferFactory getTradeOfferFactory() {
        return tradeOfferFactory;
    }

    @Override
    public void setTradeOfferFactory(ITradeOfferFactory tradeOfferFactory) {
        this.tradeOfferFactory = tradeOfferFactory;
    }

    @Override
    public IGameFactory getGameFactory() {
        return gameFactory;
    }

    @Override
    public void setGameFactory(IGameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    public INHLEventsFactory getNHLEventsFactory() {
        return inhlEventsFactory;
    }

    public void setNHLEventsFactory(INHLEventsFactory inhlEventsFactory) {
        this.inhlEventsFactory = inhlEventsFactory;
    }

    @Override
    public IGameScheduleFactory getGameScheduleFactory() {
        return gameScheduleFactory;
    }

    @Override
    public void setGameScheduleFactory(IGameScheduleFactory gameScheduleFactory) {
        this.gameScheduleFactory = gameScheduleFactory;
    }

    @Override
    public ITeamStandingFactory getTeamStandingFactory() {
        return teamStandingFactory;
    }

    @Override
    public void setTeamStandingFactory(ITeamStandingFactory teamStandingFactory) {
        this.teamStandingFactory = teamStandingFactory;
    }

    @Override
    public ILeagueFactory getLeagueFactory() {
        return leagueFactory;
    }

    @Override
    public void setLeagueFactory(ILeagueFactory leagueFactory) {
        this.leagueFactory = leagueFactory;
    }

    @Override
    public IDivisionFactory getDivisionFactory() {
        return divisionFactory;
    }

    @Override
    public void setDivisionFactory(IDivisionFactory divisionFactory) {
        this.divisionFactory = divisionFactory;
    }
}
