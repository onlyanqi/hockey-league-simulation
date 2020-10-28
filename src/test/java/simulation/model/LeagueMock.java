package simulation.model;

import db.data.*;

import java.util.ArrayList;
import java.util.List;

public class LeagueMock implements ILeagueFactory {

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
    public int addLeague(League league) throws Exception {
        league = new League(1);
        return league.getId();
    }

    private FreeAgent formFreeAgent() throws Exception {
        FreeAgent freeAgent = new FreeAgent();

        freeAgent.setId(1);
        List<Player> playerList = new ArrayList<>();

        IPlayerFactory playerFactory = new PlayerMock();
        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(5, playerFactory);
        playerList.add(player);

        freeAgent.setPlayerList(playerList);

        return freeAgent;
    }

    public Trading getTrading() throws Exception {
        ITradingFactory tradingFactory = new TradingMock();
        Trading trading = new Trading(1, tradingFactory);
        return trading;
    }

    public List<TradeOffer> getTradeOfferList() throws Exception {
        List<TradeOffer> tradeOfferList = new ArrayList<>();
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        tradeOfferList.add(tradeOffer);
        tradeOffer = new TradeOffer(2, tradeOfferFactory);
        tradeOfferList.add(tradeOffer);
        return tradeOfferList;
    }

    @Override
    public void loadLeagueById(int id, League league) throws Exception {

        switch (new Long(id).intValue()){
            case 1:
                //all correct data
                league.setName("League1");
                league.setConferenceList(formConferenceList());
                league.setFreeAgent(formFreeAgent());
                league.setTrading(getTrading());
                league.setTradingOfferList(getTradeOfferList());
                break;

            case 2:
                //name null
                league.setName(null);
                league.setConferenceList(formConferenceList());
                league.setFreeAgent(formFreeAgent());
                league.setTrading(getTrading());
                league.setTradingOfferList(getTradeOfferList());
                break;

            case 3:
                //end date less than start date
                league.setName("Invalid Date");
                league.setConferenceList(formConferenceList());
                league.setFreeAgent(formFreeAgent());
                league.setTrading(getTrading());
                league.setTradingOfferList(getTradeOfferList());
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
