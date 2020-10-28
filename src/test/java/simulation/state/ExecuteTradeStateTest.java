package simulation.state;

import db.data.*;
import org.junit.Test;
import simulation.model.*;

import java.time.LocalDate;
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
    public void actionTest() {

    }

    @Test
    public void isListNotEmptyTest() {
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
        for(int i=0;i<500;i++) {
            assertTrue(state.loopAllTeamsForTradeInitiation(league));
        }
        league.setConferenceList(null);
        assertFalse(state.loopAllTeamsForTradeInitiation(league));
    }

    @Test
    public void tradingLogicTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();
        ITeamFactory teamFactory = new TeamMock();
        ILeagueFactory leagueFactory = new LeagueMock();

        Team team = new Team(5, teamFactory);
        League league = new League(1, leagueFactory);

        for(int i = 0; i < 50 ; i++) {
            state.tradingLogic(team, league);
            double beforeTradeStrength = team.getStrength();
            if(league.getTradingOfferList() != null && league.getTradingOfferList().size() > 0){
                assertNotNull(league.getTradingOfferList().get(0).getStatus());
                double afterTradeStrength = team.getStrength();
                assertTrue(afterTradeStrength >= beforeTradeStrength);
            }
        }
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
        Player toPlayer = new Player(5, playerFactory);

        swap.put(TRADING, trading);
        swap.put(FROMTEAM, fromTeam);
        swap.put(TOTEAM, toTeam);
        swap.put(FROMPLAYER, fromPlayer);
        swap.put(TOPLAYER, toPlayer);

        assertTrue(state.acceptRejectTradeOffer(swap));
    }

    @Test
    public void updateTradingDetailsTest() throws Exception {
        Map<String, Object> tradeDetails = new HashMap<>();
        ITeamFactory teamFactory = new TeamMock();
        IPlayerFactory playerFactory = new PlayerMock();
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();

        Team fromTeam = new Team(1, teamFactory);
        Team toTeam = new Team(2, teamFactory);
        Player fromPlayer = new Player(12, playerFactory);
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
        for (Player soldPlayer : toTeam.getPlayerList()) {
            if (fromPlayer.getId() == soldPlayer.getId()) {
                isPlayerSwapped = true;
            }
            if (toPlayer.getId() == soldPlayer.getId()) {
                isPlayerNotSwapped = true;
            }
        }

        assertTrue(isPlayerSwapped);
        assertFalse(isPlayerNotSwapped);

        for (Player boughtPlayer : fromTeam.getPlayerList()) {
            if (toPlayer.getId() == boughtPlayer.getId()) {
                isPlayerSwapped = true;
            }
            if (fromPlayer.getId() == boughtPlayer.getId()) {
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
        tradeDetails.put(FROMTEAM, fromTeam);
        tradeDetails.put(TOTEAM, toTeam);
        tradeDetails.put(TRADEOFFER, tradeOffer);

        state.updateTradingDetails(tradeDetails);

        boolean isCaptainCreated = false;

        for (Player soldPlayer : fromTeam.getPlayerList()) {
            if (soldPlayer.isCaptain()) {
                isCaptainCreated = true;
                break;
            }
        }

        assertTrue(isCaptainCreated);

        isCaptainCreated = false;

        for (Player boughtPlayer : toTeam.getPlayerList()) {
            if (boughtPlayer.isCaptain()) {
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
        Player newPlayer = new Player(2, playerFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        assertTrue(state.checkTeamStrength(team, newPlayer, existingPlayer));

        existingPlayer = new Player(3, playerFactory);
        team.getPlayerList().add(existingPlayer);
        team.setStrength();
        assertFalse(state.checkTeamStrength(team, newPlayer, existingPlayer));
    }

    @Test
    public void findBestSwapPlayerTest() throws Exception {
        ITeamFactory teamFactory = new TeamMock();
        ILeagueFactory leagueFactory = new LeagueMock();
        IPlayerFactory playerFactory = new PlayerMock();

        Team team = new Team(1, teamFactory);
        League league = new League(1, leagueFactory);
        Player player = new Player(5, playerFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        Map<String, Object> players = new HashMap<>();
        state.findBestSwapPlayer(team, league, player, players);

        assertNotNull(players.get(FROMPLAYER));
        assertNotNull(players.get(TOPLAYER));
        assertNotNull(players.get(TOTEAM));

        Player fromPlayer = (Player) players.get(FROMPLAYER);
        Player toPlayer = (Player) players.get(TOPLAYER);

        assertTrue(fromPlayer.getStrength() <= toPlayer.getStrength());
        assertNotEquals(toPlayer.getId(), player.getId());

        player = new Player(1, playerFactory);
        state.findBestSwapPlayer(team, league, player, players);

        assertNotNull(players);
        assertNotNull(players.get(FROMPLAYER));
        assertNotNull(players.get(TOPLAYER));
        assertNotNull(players.get(TOTEAM));

        toPlayer = (Player) players.get(TOPLAYER);

        assertEquals(toPlayer.getId(), player.getId());
    }

    @Test
    public void algorithmToFindSwapPlayerTest() throws Exception {
        ITeamFactory teamFactory = new TeamMock();
        ILeagueFactory leagueFactory = new LeagueMock();
        IPlayerFactory playerFactory = new PlayerMock();
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();

        Team team = new Team(5, teamFactory);
        League league = new League(1, leagueFactory);
        Player weakestPlayer = new Player(5, playerFactory);
        Player swapPlayer = new Player(5, playerFactory);
        TradeOffer tradeOffer = new TradeOffer(10, tradeOfferFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        Player newPlayer = state.algorithmToFindSwapPlayer(team, league, weakestPlayer, swapPlayer);

        assertTrue(newPlayer.getStrength() >= swapPlayer.getStrength());

        swapPlayer = null;
        weakestPlayer = new Player(1, playerFactory);
        tradeOffer.setFromPlayerId(12);
        newPlayer = state.algorithmToFindSwapPlayer(team, league, weakestPlayer, swapPlayer);

        assertTrue(weakestPlayer.getId() == newPlayer.getId());
    }

    @Test
    public void checkExistingTradeOfferTest() throws Exception {
        int fromPlayerId = 1;
        int toPlayerId = 2;
        int leagueId = 1;
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();
        List<TradeOffer> tradeOfferList = tradeOfferFactory.loadTradingOfferDetailsByLeagueId(leagueId);

        ExecuteTradeState state = new ExecuteTradeState();
        assertFalse(state.checkExistingTradeOffer(fromPlayerId, toPlayerId, tradeOfferList));

        fromPlayerId = 10;
        toPlayerId = 11;

        assertTrue(state.checkExistingTradeOffer(fromPlayerId, toPlayerId, tradeOfferList));
    }

    @Test
    public void getWeakestPlayerListTest() throws Exception {
        ILeagueFactory leagueFactory = new LeagueMock();
        IPlayerFactory playerFactory = new PlayerMock();

        Team team = new Team();
        League league = new League(1, leagueFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        List<Player> weakPlayerList = state.getWeakestPlayerList(team, league);

        assertEquals(weakPlayerList.size(), 0);

        Player strongPlayer1 = new Player(10, playerFactory);
        Player strongPlayer2 = new Player(11, playerFactory);

        List<Player> playerList = new ArrayList<>();
        playerList.add(strongPlayer1);
        playerList.add(strongPlayer2);
        team.setPlayerList(playerList);

        weakPlayerList = state.getWeakestPlayerList(team, league);

        assertNotNull(weakPlayerList);
        assertTrue(weakPlayerList.size() > 0);
    }

    @Test
    public void checkCurrentTradeOfferTest() throws Exception {
        ITeamFactory teamFactory = new TeamMock();
        ILeagueFactory leagueFactory = new LeagueMock();
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();

        Team team = new Team(1, teamFactory);
        League league = new League(1, leagueFactory);
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        league.getTradingOfferList().add(tradeOffer);
        league.getTradingOfferList().add(tradeOffer);
        league.getTradingOfferList().add(tradeOffer);

        ExecuteTradeState state = new ExecuteTradeState();

        assertFalse(state.checkCurrentTradeOffer(team, league));

        team = new Team(10, teamFactory);
        assertTrue(state.checkCurrentTradeOffer(team, league));
    }

    @Test
    public void checkTradingPeriodTest() throws Exception {
        ITradingFactory tradingFactory = new TradingMock();
        LocalDate currentDate = LocalDate.now();
        Trading trading = new Trading(1, tradingFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        assertTrue(state.checkTradingPeriod(trading, currentDate));
    }

    @Test
    public void checkLossPointTest() throws Exception {
        ITeamFactory teamFactory = new TeamMock();
        ITradingFactory tradingFactory = new TradingMock();

        Team team = new Team(1, teamFactory);
        Trading trading = new Trading(1, tradingFactory);

        ExecuteTradeState state = new ExecuteTradeState();

        assertFalse(state.checkLossPoint(team, trading));

        team = new Team(5, teamFactory);

        assertTrue(state.checkLossPoint(team, trading));
    }

}