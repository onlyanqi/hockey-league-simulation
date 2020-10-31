package simulation.mock;

import db.data.*;
import simulation.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeagueMock implements ILeagueFactory {
    static final String FREEAGENT = "FreeAgent";

    public List<Conference> formConferenceList() throws Exception {
        List<Conference> conferenceList = new ArrayList<>();

        IConferenceFactory conferenceFactory = new ConferenceMock();
        Conference conference = new Conference(1, conferenceFactory);
        conferenceList.add(conference);

        conference = new Conference(2, conferenceFactory);
        conferenceList.add(conference);

        return conferenceList;
    }

    public List<Conference> formCreateTeamConferenceList() throws Exception {
        List<Conference> conferenceList = new ArrayList<>();

        IConferenceFactory conferenceFactory = new ConferenceMock();
        Conference conference = new Conference(1, conferenceFactory);
        conferenceList.add(conference);

        conference = new Conference(4, conferenceFactory);
        conferenceList.add(conference);

        return conferenceList;
    }

    public List<Coach> formCoachList() throws Exception {
        List<Coach> coachList = new ArrayList<>();

        ICoachFactory coachFactory = new CoachMock();

        for (int i = 0; i < 6; i++) {
            Coach coach = new Coach(i, coachFactory);
            coachList.add(coach);
            coachList.add(coach);
        }

        return coachList;
    }

    public List<Manager> formManagerList() throws Exception {
        List<Manager> managerList = new ArrayList<>();

        IManagerFactory managerFactory = new ManagerMock();

        for(int i =1; i<6; i++){
            Manager manager = new Manager(i,managerFactory);
            managerList.add(manager);
        }
        return managerList;
    }

    @Override
    public int addLeague(League league) throws Exception {
        league = new League(1);
        return league.getId();
    }

    private FreeAgent formFreeAgent() throws Exception {
        IFreeAgentFactory freeAgentFactory = new FreeAgentMock();
        FreeAgent freeAgent = new FreeAgent(6,freeAgentFactory);
        return freeAgent;
    }

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        IPlayerFactory playerFactory = new PlayerMock();
        for(int i=1;i<22;i++){
            Player player = new Player(i, playerFactory);
            playerList.add(player);
        }

        return playerList;
    }

    public Trading getTrading() throws Exception {
        ITradingFactory tradingFactory = new TradingMock();
        Trading trading = new Trading(1, tradingFactory);
        return trading;
    }

    public List<TradeOffer> getTradeOfferList(int leagueId) throws Exception {
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();
        return tradeOfferFactory.loadTradeOfferDetailsByLeagueId(leagueId);
    }



    @Override
    public void loadLeagueById(int id, League league) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                //all correct data
                league.setName("League1");
                league.setConferenceList(formConferenceList());
                league.setFreeAgent(formFreeAgent());
                league.setTrading(getTrading());
                league.setCoachList(formCoachList());
                league.setManagerList(formManagerList());
                league.setTradingOfferList(getTradeOfferList(1));
                league.setCurrentDate(LocalDate.now());
                break;

            case 2:
                //name null
                league.setName(null);
                league.setConferenceList(formConferenceList());
                league.setManagerList(formManagerList());
                league.setFreeAgent(formFreeAgent());
                league.setCoachList(formCoachList());
                league.setTrading(getTrading());
                league.setTradingOfferList(getTradeOfferList(2));
                league.setCurrentDate(LocalDate.now());
                break;

            case 3:
                //end date less than start date
                league.setName("Invalid Date");
                league.setConferenceList(formConferenceList());
                league.setCoachList(formCoachList());
                league.setManagerList(formManagerList());
                league.setFreeAgent(formFreeAgent());
                league.setTrading(getTrading());
                league.setTradingOfferList(getTradeOfferList(3));
                league.setCurrentDate(LocalDate.now());
                break;

            case 4:
                //all correct data
                league.setName("League4");
                league.setConferenceList(formCreateTeamConferenceList());
                league.setFreeAgent(formFreeAgent());
                league.setTrading(getTrading());
                league.setCoachList(formCoachList());
                league.setManagerList(formManagerList());
                league.setTradingOfferList(getTradeOfferList(1));
                league.setCurrentDate(LocalDate.now());
                break;
        }

    }

    @Override
    public void loadLeagueByName(String leagueName, int userId, League league) throws Exception {
        league.setName("League1");
        league.setConferenceList(formConferenceList());
        league.setFreeAgent(formFreeAgent());
    }

    public List formLeagueList() throws Exception {
        List<League> leagueList = new ArrayList<>();

        League league = new League(1);
        league.setName("League1");
        leagueList.add(league);

        league = new League(2);
        league.setName("League2");
        leagueList.add(league);

        return leagueList;
    }

    @Override
    public List<League> loadLeagueListByUserId(int userId) throws Exception {
        return formLeagueList();
    }

}
