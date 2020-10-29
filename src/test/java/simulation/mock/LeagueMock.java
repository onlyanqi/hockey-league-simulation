package simulation.mock;

import db.data.*;
import simulation.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeagueMock implements ILeagueFactory {
    static final String FREEAGENT = "FreeAgent";
    static final String MANAGER = "Manager";

    public List formConferenceList() throws Exception {
        List<Conference> conferenceList = new ArrayList<>();

        IConferenceFactory conferenceFactory = new ConferenceMock();
        Conference conference = new Conference(1, conferenceFactory);
        conferenceList.add(conference);

        conference = new Conference(2, conferenceFactory);
        conferenceList.add(conference);

        return conferenceList;
    }

    @Override
    public int addLeague(
            League league) throws Exception {
        league = new League(1);
        return league.getId();
    }

    private FreeAgent formFreeAgent() throws Exception {
        FreeAgent freeAgent = new FreeAgent();
        freeAgent.setId(1);
        List<Player> playerList = new ArrayList<>();
        for(int i=0;i<30;i++){
            Player player = new Player();
            player.setId(i);
            player.setIsFreeAgent(true);
            player.setName(FREEAGENT+i);
            player.setAge(25);
            player.setChecking(10);
            player.setSaving(0);
            player.setSkating(11);
            player.setShooting(12);
            player.setFreeAgentId(1);
            player.setCaptain(false);
            if(i%2 == 0){
                player.setPosition(Player.Position.forward);
            }else{
                player.setPosition(Player.Position.defense);
            }
            if(i == 3 || i ==4 || i==5 || i==10){
                player.setPosition(Player.Position.goalie);
                player.setSaving(10);
                player.setChecking(15);
                player.setSkating(16);
                player.setShooting(19);
            }
            playerList.add(player);
        }
        freeAgent.setPlayerList(playerList);
        return freeAgent;
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

    public List<Manager> formManagerList(){
        List<Manager> managerList = new ArrayList<>();
        for(int i =0; i<5; i++){
            Manager manager = new Manager();
            manager.setLeagueId(0);
            manager.setId(0);
            manager.setTeamId(0);
            manager.setName(MANAGER+i);
            managerList.add(manager);
        }
        return managerList;
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
                league.setTrading(getTrading());
                league.setTradingOfferList(getTradeOfferList(2));
                league.setCurrentDate(LocalDate.now());
                break;

            case 3:
                //end date less than start date
                league.setName("Invalid Date");
                league.setConferenceList(formConferenceList());
                league.setManagerList(formManagerList());
                league.setFreeAgent(formFreeAgent());
                league.setTrading(getTrading());
                league.setTradingOfferList(getTradeOfferList(3));
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
