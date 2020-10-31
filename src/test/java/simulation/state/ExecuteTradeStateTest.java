package simulation.state;

import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.*;
import simulation.model.*;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.*;
import static org.junit.Assert.*;

public class ExecuteTradeStateTest {

    private static final String FROMPLAYER = "fromPlayer";
    private static final String FROMTEAM = "fromTeam";
    private static final String TRADEOFFER = "tradeOffer";
    private static final String TOPLAYER = "toPlayer";
    private static final String TOTEAM = "toTeam";
    private static final String TRADING = "trading";
    private static final String ACCEPTED = "accepted";
    private static final String REJECTED = "rejected";
    private static ILeagueFactory leagueFactory;
    private static ITeamFactory teamFactory;
    private static IPlayerFactory playerFactory;
    private static ITradeOfferFactory tradeOfferFactory;
    private static ITradingFactory tradingFactory;
    private static IUserFactory userFactory;

    @BeforeClass
    public static void init(){
        leagueFactory = new LeagueMock();
        teamFactory = new TeamMock();
        playerFactory = new PlayerMock();
        tradeOfferFactory = new TradeOfferMock();
        tradingFactory = new TradingMock();
        userFactory = new UserMock();
    }

    @Test
    public void defaultConstructorTest(){
        ExecuteTradeState state = new ExecuteTradeState();
        assertTrue(state instanceof ExecuteTradeState);
        assertTrue(state instanceof ISimulateState);
    }

    @Test
    public void initConstructorTest() throws Exception {
        HockeyContext hockeyContext = new HockeyContext();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
        ExecuteTradeState state = new ExecuteTradeState(hockeyContext);
        assertNotNull(state.getLeague());
    }

    @Test
    public void getLeagueTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();
        League league = new League(1, leagueFactory);
        state.setLeague(league);
        assertEquals(1, state.getLeague().getId());
        assertEquals(1, state.getLeague().getCreatedBy());
    }

    @Test
    public void setLeagueTest() throws Exception {
        League league = new League();
        league.setId(1);
        league.setCreatedBy(1);

        ExecuteTradeState state = new ExecuteTradeState();
        state.setLeague(league);

        assertEquals(1, state.getLeague().getId());
        assertEquals(1, state.getLeague().getCreatedBy());
    }

    @Test
    public void actionTest() throws Exception {
        HockeyContext hockeyContext = new HockeyContext();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
        ExecuteTradeState state = new ExecuteTradeState(hockeyContext);
        assertTrue(state.action() instanceof ISimulateState);
        assertFalse(state.action() instanceof ExecuteTradeState);
    }

    public boolean isNotNull(Object input){
        boolean isNotNull = true;

        if(input == null){
            isNotNull = false;
        }

        return isNotNull;
    }

    @Test
    public void isListNotEmptyTest() {
        ExecuteTradeState state = new ExecuteTradeState();
        assertFalse(state.isListNotEmpty(null));
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        assertTrue(state.isListNotEmpty(list));
    }

    @Test
    public void isNotNullTest(){
        String a = null;
        ExecuteTradeState state = new ExecuteTradeState();
        assertFalse(state.isNotNull(a));
        a = "a";
        assertTrue(state.isNotNull(a));
    }

    @Test
    public void loopAllTeamsForTradeInitiationTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();
        League league = new League(1, leagueFactory);
        assertTrue(state.loopAllTeamsForTradeInitiation(league));
        league = new League();
        assertFalse(state.loopAllTeamsForTradeInitiation(league));
    }

    @Test
    public void tradingLogicTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();

        Team team = new Team(5, teamFactory);
        League league = new League(1, leagueFactory);

        for(int i = 0; i < 50 ; i++) {
            state.tradingLogic(team, league);
            double beforeTradeStrength = team.getStrength();
            if(isNotNull(league.getTradingOfferList()) && league.getTradingOfferList().size() > 0){
                assertNotNull(league.getTradingOfferList().get(0).getStatus());
                double afterTradeStrength = team.getStrength();
                assertTrue(afterTradeStrength >= beforeTradeStrength);
            }
        }
    }

    @Test
    public void createTradeOfferTest() throws Exception {
        Map<String, Object> swap = new HashMap<>();

        League league = new League(1, leagueFactory);
        Player player1 = new Player(1, playerFactory);
        Player player2 = new Player(3, playerFactory);
        Trading trading1 = league.getGamePlayConfig().getTrading();

        swap.put(FROMPLAYER, player1);
        swap.put(TOPLAYER, player2);

        ExecuteTradeState state = new ExecuteTradeState();
        state.setLeague(league);

        state.createTradeOffer(swap);

        Trading trading2 = (Trading) swap.get(TRADING);
        TradeOffer tradeOffer = (TradeOffer) swap.get(TRADEOFFER);

        assertNotNull(trading2);
        assertNotNull(tradeOffer);

        assertEquals(trading1.getMaxPlayersPerTrade(), trading2.getMaxPlayersPerTrade());
        assertEquals(trading1.getLossPoint(), trading2.getLossPoint());

        assertEquals(tradeOffer.getFromPlayerId(), player1.getId());
        assertEquals(tradeOffer.getToPlayerId(), player2.getId());
    }

    @Test
    public void performTradeTest() throws Exception {
        Map<String, Object> swap = new HashMap<>();

        Player player1 = new Player(3, playerFactory);
        Player player2 = new Player(1, playerFactory);
        Team team1 = new Team(1, teamFactory);
        Team team2 = new Team(3, teamFactory);
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        Trading trading = new Trading(1, tradingFactory);

        swap.put(FROMPLAYER, player1);
        swap.put(TOPLAYER, player2);
        swap.put(FROMTEAM, team1);
        swap.put(TOTEAM, team2);
        swap.put(TRADEOFFER, tradeOffer);
        swap.put(TRADING, trading);

        HockeyContext hockeyContext = new HockeyContext();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
        ExecuteTradeState state = new ExecuteTradeState(hockeyContext);
        state.performTrade(swap);

        assertEquals(tradeOffer.getStatus(), ACCEPTED);
    }

    /*
    @Test
    public void performUserTradeTest() throws Exception {
        ByteArrayInputStream byteArrayInputStream = null;
        Map<String, Object> swap = null;
        List<String> userInputs = new ArrayList<>(Arrays.asList("A", "a", "R", "r", "A ", "  r", ""));

        for(String userInput : userInputs){
            swap = new HashMap<>();

            Player player1 = new Player(1, playerFactory);
            Player player2 = new Player(3, playerFactory);
            Team team1 = new Team(1, teamFactory);
            Team team2 = new Team(3, teamFactory);
            TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
            Trading trading = new Trading(1, tradingFactory);

            swap.put(FROMPLAYER, player1);
            swap.put(TOPLAYER, player2);
            swap.put(FROMTEAM, team1);
            swap.put(TOTEAM, team2);
            swap.put(TRADEOFFER, tradeOffer);
            swap.put(TRADING, trading);

            HockeyContext hockeyContext = new HockeyContext();
            User user = new User(1, userFactory);
            hockeyContext.setUser(user);
            ExecuteTradeState state = new ExecuteTradeState(hockeyContext);

            byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(byteArrayInputStream);
            state.performUserTrade(swap);

            if(userInput.equalsIgnoreCase("a".trim())) {
                assertEquals(tradeOffer.getStatus(), (ACCEPTED));
            } else {
                assertEquals(tradeOffer.getStatus(), (REJECTED));
            }
        }
    }
     */

    @Test
    public void acceptRejectTradeOfferTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();
        Map<String, Object> swap = new HashMap<>();

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

        Team fromTeam = new Team(1, teamFactory);
        Team toTeam = new Team(2, teamFactory);
        Player fromPlayer = new Player(30, playerFactory);
        Player toPlayer = new Player(31, playerFactory);
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
        Team team = new Team(1, teamFactory);
        Player existingPlayer = team.getPlayerList().get(0);
        Player newPlayer = new Player(5, playerFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        assertTrue(state.checkTeamStrength(team, newPlayer, existingPlayer));

        existingPlayer = new Player(7, playerFactory);
        team.getPlayerList().add(existingPlayer);
        team.setStrength();
        assertFalse(state.checkTeamStrength(team, newPlayer, existingPlayer));
    }

    @Test
    public void findBestSwapPlayerTest() throws Exception {
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

        player = new Player(4, playerFactory);
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
        Map<String, Object> swap = new HashMap<>();
        Team team = new Team(5, teamFactory);
        Player weakestPlayer = new Player(5, playerFactory);
        Player swapPlayer = new Player(5, playerFactory);
        TradeOffer tradeOffer = new TradeOffer(10, tradeOfferFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        Player newPlayer = state.algorithmToFindSwapPlayer(team, weakestPlayer, swapPlayer, swap);

        assertTrue(newPlayer.getStrength() >= swapPlayer.getStrength());

        swapPlayer = null;
        weakestPlayer = new Player(4, playerFactory);
        tradeOffer.setFromPlayerId(12);
        newPlayer = state.algorithmToFindSwapPlayer(team, weakestPlayer, swapPlayer, swap);

        assertTrue(weakestPlayer.getId() == newPlayer.getId());
    }

    @Test
    public void getWeakestPlayerListTest() throws Exception {
        Team team = new Team();
        League league = new League(1, leagueFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        List<Player> weakPlayerList = state.getWeakestPlayerList(team, league);

        assertNull(weakPlayerList);

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
        LocalDate currentDate = LocalDate.now();
        Trading trading = new Trading(1, tradingFactory);

        ExecuteTradeState state = new ExecuteTradeState();
        assertTrue(state.checkTradingPeriod(trading, currentDate));
    }

    @Test
    public void checkLossPointTest() throws Exception {
        Team team = new Team(1, teamFactory);
        Trading trading = new Trading(1, tradingFactory);
        ExecuteTradeState state = new ExecuteTradeState();

        assertFalse(state.checkLossPoint(team, trading));

        team = new Team(5, teamFactory);

        assertTrue(state.checkLossPoint(team, trading));
    }

    @Test
    public void getRandomDoubleTest(){
        ExecuteTradeState state = new ExecuteTradeState();
        double randomDouble = state.getRandomDouble();
        assertTrue(0 < randomDouble);
        assertTrue(1 > randomDouble);
        assertFalse(2 < randomDouble);
        assertNotEquals(0, randomDouble);
    }

    @Test
    public void removeObjectFromListTest() throws Exception {
        List<Player> list = new ArrayList<>();
        Player player = new Player(1, playerFactory);
        list.add(player);
        player = new Player(5, playerFactory);
        list.add(player);
        boolean isPlayerExist = false;
        for(Player player1 : list){
            if(player1.getId() == 5){
                isPlayerExist = true;
            }
        }
        assertTrue(isPlayerExist);

        ExecuteTradeState state = new ExecuteTradeState();
        state.removeObjectFromList(list, player);

        isPlayerExist = false;

        for(Player player1 : list){
            if(player1.getId() == 5){
                isPlayerExist = true;
            }
        }
        assertFalse(isPlayerExist);
    }

}