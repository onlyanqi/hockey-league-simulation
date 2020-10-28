package simulation.state;

import db.data.*;
import org.junit.Test;
import simulation.model.*;

import java.util.*;

import static org.junit.Assert.*;

public class ExecuteTradeStateTest {

    private final String FROMPLAYER = "fromPlayer";
    private final String FROMTEAM = "fromTeam";
    private final String TRADEOFFER = "tradeOffer";
    private final String TOPLAYER = "toPlayer";
    private final String TOTEAM = "toTeam";
    private final String TRADING = "trading";
    private final String ACCEPTED = "accepted";

    @Test
    public void actionTest(){

    }

    @Test
    public void isListNotEmptyTest(){
        ExecuteTradeState state = new ExecuteTradeState();
        assertFalse(state.isListNotEmpty(null));
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        assertTrue(state.isListNotEmpty(list));
    }

    @Test
    public void loopAllTeamsForTradeInitiationTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        assertTrue(state.loopAllTeamsForTradeInitiation(league));
        league.setConferenceList(null);
        assertFalse(state.loopAllTeamsForTradeInitiation(league));
    }

    @Test
    public void tradingLogicTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();
        ITeamFactory teamFactory = new TeamMock();
        ILeagueFactory leagueFactory = new LeagueMock();

        Team team = new Team(1, teamFactory);
        League league = new League(1, leagueFactory);

        Map<String, Object> swap = state.tradingLogic(team, league);
        assertNotNull(swap);

        Player fromPlayer = (Player) swap.get(FROMPLAYER);
        assertNotNull(fromPlayer);

        Team fromTeam = (Team) swap.get(FROMTEAM);
        assertNotNull(fromTeam);

        Player toPlayer = (Player) swap.get(TOPLAYER);
        assertNotNull(toPlayer);

        Team toTeam = (Team) swap.get(TOTEAM);
        assertNotNull(toTeam);

        TradeOffer tradeOffer = (TradeOffer) swap.get(TRADEOFFER);
        assertNotNull(tradeOffer);

        assertEquals(tradeOffer.getFromPlayerId(), fromPlayer.getId());
        assertEquals(tradeOffer.getToPlayerId(), toPlayer.getId());
        assertEquals(tradeOffer.getFromTeamId(), fromTeam.getId());
        assertEquals(tradeOffer.getToTeamId(), toTeam.getId());

        assertNotEquals(tradeOffer.getFromPlayerId(), toPlayer.getId());
        assertNotEquals(tradeOffer.getToPlayerId(), fromPlayer.getId());
        assertNotEquals(tradeOffer.getFromTeamId(), toTeam.getId());
        assertNotEquals(tradeOffer.getToTeamId(), fromTeam.getId());
    }

    @Test
    public void acceptRejectTradeOfferTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();
        Map<String, Object> swap = new HashMap<>();
        ITeamFactory teamFactory = new TeamMock();
        IPlayerFactory playerFactory = new PlayerMock();
        ITradingFactory tradingFactory = new TradingMock();

        Trading trading = new Trading(1, tradingFactory);
        Team fromTeam = new Team(1, teamFactory);
        Team toTeam = new Team(2, teamFactory);

        Player fromPlayer = new Player(1, playerFactory);
        Player toPlayer = new Player(2, playerFactory);

        swap.put(TRADING, trading);
        swap.put(FROMTEAM, fromTeam);
        swap.put(TOTEAM, toTeam);
        swap.put(FROMPLAYER, fromPlayer);
        swap.put(TOPLAYER, toPlayer);

        assertTrue(state.acceptRejectTradeOffer(swap));

        swap.put(FROMTEAM, toTeam);
        swap.put(TOTEAM, fromTeam);
        swap.put(TRADING, null);

        assertFalse(state.acceptRejectTradeOffer(swap));
    }

    @Test
    public void updateTradingDetailsTest() throws Exception {
        Map<String, Object> tradeDetails = new HashMap<>();
        ITeamFactory teamFactory = new TeamMock();
        IPlayerFactory playerFactory = new PlayerMock();
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();

        Team fromTeam = new Team(1, teamFactory);
        Team toTeam = new Team(2, teamFactory);
        Player fromPlayer = new Player(10, playerFactory);
        Player toPlayer = new Player(11, playerFactory);
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);

        fromTeam.getPlayerList().add(fromPlayer);
        toTeam.getPlayerList().add(toPlayer);

        tradeDetails.put(FROMPLAYER, fromPlayer);
        tradeDetails.put(TOPLAYER, toPlayer);
        tradeDetails.put(FROMTEAM, fromTeam);
        tradeDetails.put(TOTEAM, toTeam);
        tradeDetails.put(TRADEOFFER, tradeOffer);

        ExecuteTradeState state = new ExecuteTradeState();
        state.updateTradingDetails(tradeDetails);

        assertEquals(tradeOffer.getStatus(), ACCEPTED);

        boolean isPlayerSwapped = false;
        boolean isPlayerNotSwapped = false;
        for(Player soldPlayer : toTeam.getPlayerList()){
            if(fromPlayer.getId() == soldPlayer.getId()){
                isPlayerSwapped = true;
            }
            if(toPlayer.getId() == soldPlayer.getId()){
                isPlayerNotSwapped = true;
            }
        }

        assertTrue(isPlayerSwapped);
        assertFalse(isPlayerNotSwapped);

        for(Player boughtPlayer : fromTeam.getPlayerList()){
            if(toPlayer.getId() == boughtPlayer.getId()){
                isPlayerSwapped = true;
            }
            if(fromPlayer.getId() == boughtPlayer.getId()){
                isPlayerNotSwapped = true;
            }
        }

        assertTrue(isPlayerSwapped);
        assertFalse(isPlayerNotSwapped);

        tradeDetails = new HashMap<>();
        toPlayer.setCaptain(true);
        fromPlayer.setCaptain(true);
        tradeDetails.put(FROMPLAYER, toPlayer);
        tradeDetails.put(TOPLAYER, fromPlayer);

        state.updateTradingDetails(tradeDetails);

        boolean isCaptainCreated = false;

        for(Player soldPlayer : fromTeam.getPlayerList()){
            if(soldPlayer.isCaptain()){
                isCaptainCreated = true;
                break;
            }
        }

        assertTrue(isCaptainCreated);

        isCaptainCreated = false;

        for(Player boughtPlayer : toTeam.getPlayerList()){
            if(boughtPlayer.isCaptain()){
                isCaptainCreated = true;
                break;
            }
        }

        assertTrue(isCaptainCreated);
    }

    @Test
    public void checkTeamStrengthTest() throws Exception {
        ITeamFactory teamFactory = new TeamMock();
        IPlayerFactory playerFactory = new PlayerMock();

        Team team = new Team(1, teamFactory);
        Player existingPlayer = team.getPlayerList().get(0);
        Player newPlayer = new Player(10, playerFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        assertTrue(state.checkTeamStrength(team, newPlayer, existingPlayer));

        Player weakPlayer = new Player(11, playerFactory);
        assertFalse(state.checkTeamStrength(team, weakPlayer, existingPlayer));
    }

    @Test
    public void findBestSwapPlayerTest() throws Exception {
        ITeamFactory teamFactory = new TeamMock();
        ILeagueFactory leagueFactory = new LeagueMock();
        IPlayerFactory playerFactory = new PlayerMock();

        Team team = new Team(1, teamFactory);
        League league = new League(1, leagueFactory);
        Player player = new Player(10, playerFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        Map<String, Object> players = state.findBestSwapPlayer(team, league, player);

        assertNotNull(players);
        assertNotNull(players.get(FROMPLAYER));
        assertNotNull(players.get(TOPLAYER));
        assertNotNull(players.get(TOTEAM));

        Player fromPlayer = (Player) players.get(FROMPLAYER);
        Player toPlayer = (Player) players.get(TOPLAYER);

        assertTrue(fromPlayer.getStrength() < toPlayer.getStrength());
    }



}
