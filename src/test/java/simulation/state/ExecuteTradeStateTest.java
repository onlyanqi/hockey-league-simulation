package simulation.state;

import simulation.dao.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.*;
import simulation.model.IUser;
import simulation.model.ILeague;
import simulation.model.IPlayer;
import simulation.model.ITeam;
import simulation.model.IConference;
import simulation.model.ITradeOffer;
import simulation.model.ITrading;
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
    private static final String FROMPLAYERLISTBEFORETRADE = "fromPlayerListBeforeTrade";
    private static final String FROMPLAYERLISTAFTERTRADE = "fromPlayerListAfterTrade";
    private final String TOPLAYERLIST = "toPlayerList";
    private static ILeagueDao leagueDao;
    private static ITeamDao teamDao;
    private static IPlayerDao playerDao;
    private static ITradeOfferDao tradeOfferDao;
    private static ITradingDao tradingDao;
    private static IUserDao userDao;
    private static ILeagueFactory leagueFactory;
    private static ITeamFactory teamFactory;
    private static IPlayerFactory playerFactory;
    private static ITradeOfferFactory tradeOfferFactory;
    private static ITradingFactory tradingFactory;
    private static IUserFactory userFactory;
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;
    private static ILeague league;
    private static IUser user;

    @BeforeClass
    public static void init() throws Exception {
        hockeyContextFactory = HockeyContextConcreteMock.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        leagueFactory = hockeyContext.getLeagueFactory();
        leagueDao = leagueFactory.newLeagueDao();
        userFactory = hockeyContext.getUserFactory();
        userDao = userFactory.newUserDao();
        teamFactory = hockeyContext.getTeamFactory();
        teamDao = teamFactory.newTeamDao();
        playerFactory = hockeyContext.getPlayerFactory();
        playerDao = playerFactory.newPlayerDao();
        tradingFactory = hockeyContext.getTradingFactory();
        tradingDao = tradingFactory.newTradingDao();
        tradeOfferFactory = hockeyContext.getTradeOfferFactory();
        tradeOfferDao = tradeOfferFactory.newTradeOfferDao();
    }

    private ExecuteTradeState newStateEmptyConstructor() throws Exception {
        league = leagueFactory.newLeagueWithIdDao(1, leagueDao);
        ExecuteTradeState executeTradeState = new ExecuteTradeState();
        executeTradeState.setLeague(league);
        return executeTradeState;
    }

    private ExecuteTradeState newStateWithHockeyContext(IHockeyContext hockeyContext) throws Exception {
        return new ExecuteTradeState(hockeyContext);
    }

    @Test
    public void defaultConstructorTest() throws Exception {
        ExecuteTradeState state = newStateEmptyConstructor();
        assertTrue(state instanceof ExecuteTradeState);
        assertTrue(state instanceof ISimulateState);
    }

    @Test
    public void initConstructorTest() throws Exception {
        league = leagueFactory.newLeagueWithIdDao(1, leagueDao);
        user = userFactory.newUserWithIdDao(1, userDao);
        user.setLeague(league);
        hockeyContext.setUser(user);
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);
        assertNotNull(state.getLeague());
        assertEquals(state.getLeague().getId(), 1);
        assertNotEquals(state.getLeague().getId(), 5);
    }

    @Test
    public void getLeagueTest() throws Exception {
        ExecuteTradeState state = newStateEmptyConstructor();
        state.setLeague(league);
        assertEquals(1, state.getLeague().getId());
        assertEquals(1, state.getLeague().getCreatedBy());
    }

    @Test
    public void setLeagueTest() throws Exception {
        ILeague league = leagueFactory.newLeague();
        league.setId(1);
        league.setCreatedBy(1);

        ExecuteTradeState state = newStateEmptyConstructor();
        state.setLeague(league);

        assertEquals(1, state.getLeague().getId());
        assertEquals(1, state.getLeague().getCreatedBy());
    }

    @Test
    public void actionTest() throws Exception {
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);
        assertTrue(state.action() instanceof ISimulateState);
        assertFalse(state.action() instanceof ExecuteTradeState);
    }

    @Test
    public void loopAllTeamsForTradeInitiationTest() throws Exception {
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);
        ILeague league = leagueFactory.newLeagueWithIdDao(1, leagueDao);
        assertTrue(state.loopAllTeamsForTradeInitiation(league));
        league = leagueFactory.newLeague();
        List<IConference> conferences = new ArrayList<>();
        league.setConferenceList(conferences);
        assertFalse(state.loopAllTeamsForTradeInitiation(league));
    }

    @Test
    public void tradingLogicTest() throws Exception {
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);

        ITeam team = teamFactory.newTeamWithIdDao(5, teamDao);
        ILeague league = leagueFactory.newLeagueWithIdDao(1, leagueDao);

        for (int i = 0; i < 50; i++) {
            state.tradingLogic(team, league);
            if (league.getTradeOfferList() == null || league.getTradeOfferList().isEmpty()) {
                continue;
            } else{
                assertNotNull(league.getTradeOfferList().get(0).getStatus());
            }
        }
    }

    @Test
    public void createTradeOfferTest() throws Exception {
        Map<String, Object> swap = new HashMap<>();
        ILeague league = leagueFactory.newLeagueWithIdDao(1, leagueDao);
        IUser user = userFactory.newUserWithIdDao(1, userDao);
        user.setLeague(league);
        hockeyContext.setUser(user);
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);
        ITeam fromTeam = teamFactory.newTeamWithIdDao(1, teamDao);
        ITeam toTeam = teamFactory.newTeamWithIdDao(2, teamDao);
        IPlayer fromPlayer = fromTeam.getPlayerList().get(22);
        IPlayer toPlayer = toTeam.getPlayerList().get(19);
        List<IPlayer> fromPlayerList = new ArrayList<>(Arrays.asList(fromPlayer));
        List<IPlayer> toPlayerList = new ArrayList<>(Arrays.asList(toPlayer));
        swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerList);
        swap.put(TOPLAYERLIST, toPlayerList);
        swap.put(FROMTEAM, fromTeam);
        swap.put(TOTEAM, toTeam);

        state.setLeague(league);

        state.createTradeOffer(swap);

        ITradeOffer tradeOffer = (ITradeOffer) swap.get(TRADEOFFER);

        assertNotNull(tradeOffer);

        assertEquals(tradeOffer.getFromTeamId(), fromTeam.getId());
        assertEquals(tradeOffer.getToTeamId(), toTeam.getId());
    }

    @Test
    public void performTradeTest() throws Exception {
        Map<String, Object> swap = new HashMap<>();

        IPlayer player1 = playerFactory.newPlayerWithIdDao(3, playerDao);
        IPlayer player2 = playerFactory.newPlayerWithIdDao(1, playerDao);
        ITeam team1 = teamFactory.newTeamWithIdDao(1, teamDao);
        ITeam team2 = teamFactory.newTeamWithIdDao(3, teamDao);
        ITrading trading = tradingFactory.newTradingWithIdDao(3, tradingDao);
        league = leagueFactory.newLeagueWithIdDao(1, leagueDao);
        league.getGamePlayConfig().setTrading(trading);
        user = userFactory.newUserWithIdDao(1, userDao);
        user.setLeague(league);
        hockeyContext.setUser(user);
        List<IPlayer> fromPlayerListAfterTrade = new ArrayList<>(Arrays.asList(player1));
        List<IPlayer> toPlayerList = new ArrayList<>(Arrays.asList(player2));

        swap.put(TOPLAYERLIST, toPlayerList);
        swap.put(FROMTEAM, team1);
        swap.put(TOTEAM, team2);
        swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListAfterTrade);
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);
        state.performTrade(swap);

        List<ITradeOffer> tradeOfferList = hockeyContext.getUser().getLeague().getTradeOfferList();

        assertEquals(tradeOfferList.get(3).getStatus(), ACCEPTED);
    }


    @Test
    public void performUserTradeTest() throws Exception {
        ByteArrayInputStream byteArrayInputStream;
        Map<String, Object> swap;
        List<String> userInputs = new ArrayList<>(Arrays.asList("A\n", "a\n", "R\n", "r\n"));

        for (String userInput : userInputs) {
            swap = new HashMap<>();

            IPlayer player1 = playerFactory.newPlayerWithIdDao(1, playerDao);
            IPlayer player2 = playerFactory.newPlayerWithIdDao(3, playerDao);
            ITeam team1 = teamFactory.newTeamWithIdDao(1, teamDao);
            ITeam team2 = teamFactory.newTeamWithIdDao(3, teamDao);
            ITradeOffer tradeOffer = tradeOfferFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
            ITrading trading = tradingFactory.newTradingWithIdDao(1, tradingDao);
            List<IPlayer> fromPlayerListAfterTrade = new ArrayList<>(Arrays.asList(player1));
            List<IPlayer> toPlayerList = new ArrayList<>(Arrays.asList(player2));

            swap.put(FROMPLAYER, player1);
            swap.put(TOPLAYERLIST, toPlayerList);
            swap.put(FROMTEAM, team1);
            swap.put(TOTEAM, team2);
            swap.put(TRADEOFFER, tradeOffer);
            swap.put(TRADING, trading);
            swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListAfterTrade);

            ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);

            byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(byteArrayInputStream);
            state.performUserTrade(swap);

            ITradeOffer tradeOffer1 = (ITradeOffer) swap.get(TRADEOFFER);

            if (userInput.toLowerCase().contains("a")) {
                assertEquals(tradeOffer1.getStatus(), (ACCEPTED));
            } else {
                assertEquals(tradeOffer1.getStatus(), (REJECTED));
            }
        }
    }

    @Test
    public void acceptRejectTradeOfferTest() throws Exception {
        ITrading trading = tradingFactory.newTradingWithIdDao(3, tradingDao);
        ILeague league = leagueFactory.newLeagueWithIdDao(1, leagueDao);
        IUser user = userFactory.newUserWithIdDao(1, userDao);
        league.getGamePlayConfig().setTrading(trading);
        user.setLeague(league);
        hockeyContext.setUser(user);
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);
        Map<String, Object> swap = new HashMap<>();
        ITeam fromTeam = teamFactory.newTeamWithIdDao(1, teamDao);

        swap.put(FROMTEAM, fromTeam);

        assertTrue(state.acceptRejectTradeOffer(swap));
    }

    @Test
    public void updateTradingDetailsTest() throws Exception {
        Map<String, Object> tradeDetails = new HashMap<>();
        IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
        IHockeyContext hockeyContext = hockeyContextFactory.newHockeyContext();
        IUser user = userFactory.newUserWithIdDao(1, userDao);
        ILeague league = leagueFactory.newLeagueWithIdDao(1, leagueDao);
        user.setLeague(league);
        hockeyContext.setUser(user);
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);

        ITeam fromTeam = teamFactory.newTeamWithIdDao(1, teamDao);
        ITeam toTeam = teamFactory.newTeamWithIdDao(2, teamDao);
        IPlayer fromPlayer = playerFactory.newPlayerWithIdDao(27, playerDao);
        state.removeObjectFromList(fromTeam.getPlayerList(), fromPlayer);
        IPlayer toPlayer = playerFactory.newPlayerWithIdDao(28, playerDao);
        state.removeObjectFromList(toTeam.getPlayerList(), toPlayer);
        ITradeOffer tradeOffer = tradeOfferFactory.newTradeOfferWithIdDao(1, tradeOfferDao);

        fromTeam.getPlayerList().add(fromPlayer);
        toTeam.getPlayerList().add(toPlayer);

        List<IPlayer> fromPlayerListAfterTrade = new ArrayList<>(Arrays.asList(fromPlayer));
        List<IPlayer> toPlayerList = new ArrayList<>(Arrays.asList(toPlayer));

        tradeDetails.put(TOPLAYERLIST, toPlayerList);
        tradeDetails.put(FROMTEAM, fromTeam);
        tradeDetails.put(TOTEAM, toTeam);
        tradeDetails.put(TRADEOFFER, tradeOffer);
        tradeDetails.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListAfterTrade);

        state.updateTradingDetails(tradeDetails);

        assertEquals(tradeOffer.getStatus(), ACCEPTED);

        boolean isPlayerSwapped = false;
        boolean isPlayerNotSwapped = false;
        for (IPlayer soldPlayer : toTeam.getPlayerList()) {
            if (fromPlayer.getId() == soldPlayer.getId()) {
                isPlayerSwapped = true;
            }
            if (toPlayer.getId() == soldPlayer.getId()) {
                isPlayerNotSwapped = true;
            }
        }

        assertTrue(isPlayerSwapped);
        assertFalse(isPlayerNotSwapped);

        isPlayerSwapped = false;

        for (IPlayer boughtPlayer : fromTeam.getPlayerList()) {
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
        toPlayerList = new ArrayList<>(Arrays.asList(toPlayer));
        fromPlayerListAfterTrade = new ArrayList<>(Arrays.asList(fromPlayer));

        tradeDetails.put(TOPLAYERLIST, fromPlayerListAfterTrade);
        tradeDetails.put(FROMTEAM, fromTeam);
        tradeDetails.put(TOTEAM, toTeam);
        tradeDetails.put(TRADEOFFER, tradeOffer);
        tradeDetails.put(FROMPLAYERLISTAFTERTRADE, toPlayerList);

        state.updateTradingDetails(tradeDetails);

        boolean isCaptainCreated = false;

        for (IPlayer soldPlayer : fromTeam.getPlayerList()) {
            if (soldPlayer.isCaptain()) {
                isCaptainCreated = true;
                break;
            }
        }

        assertTrue(isCaptainCreated);

        isCaptainCreated = false;

        for (IPlayer boughtPlayer : toTeam.getPlayerList()) {
            if (boughtPlayer.isCaptain()) {
                isCaptainCreated = true;
                break;
            }
        }

        assertTrue(isCaptainCreated);
    }

    @Test
    public void getWeakestPlayerListTest() throws Exception {
        ITeam team = teamFactory.newTeamWithIdDao(1, teamDao);
        ILeague league = leagueFactory.newLeagueWithIdDao(1, leagueDao);

        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);
        List<IPlayer> weakPlayerList = state.getWeakestPlayerList(team, league);

        assertNotNull(weakPlayerList);
        assertTrue(weakPlayerList.size() > 0);
    }

    @Test
    public void checkCurrentTradeOfferTest() throws Exception {
        ITeam team = teamFactory.newTeamWithIdDao(5, teamDao);
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);

        assertFalse(state.checkCurrentTradeOffer(team));

        team = teamFactory.newTeamWithIdDao(10, teamDao);
        assertTrue(state.checkCurrentTradeOffer(team));
    }

    @Test
    public void checkTradingPeriodTest() throws Exception {
        LocalDate currentDate = LocalDate.now();
        ITrading trading = tradingFactory.newTradingWithIdDao(1, tradingDao);

        ExecuteTradeState state = newStateEmptyConstructor();
        assertTrue(state.checkTradingPeriod(trading, currentDate));
    }

    @Test
    public void checkLossPointTest() throws Exception {
        ITeam team = teamFactory.newTeamWithIdDao(1, teamDao);
        ITrading trading = tradingFactory.newTradingWithIdDao(1, tradingDao);
        ExecuteTradeState state = newStateEmptyConstructor();

        assertFalse(state.checkLossPoint(team, trading));

        team = teamFactory.newTeamWithIdDao(5, teamDao);

        assertTrue(state.checkLossPoint(team, trading));
    }

    @Test
    public void getRandomDoubleTest() throws Exception {
        ExecuteTradeState state = newStateEmptyConstructor();
        double randomDouble = state.getRandomDouble();
        assertTrue(0 < randomDouble);
        assertTrue(1 > randomDouble);
        assertFalse(2 < randomDouble);
        assertNotEquals(0, randomDouble);
    }

    @Test
    public void removeObjectFromListTest() throws Exception {
        List<IPlayer> list = new ArrayList<>();
        IPlayer player = playerFactory.newPlayerWithIdDao(1, playerDao);
        list.add(player);
        player = playerFactory.newPlayerWithIdDao(5, playerDao);
        list.add(player);
        boolean isPlayerExist = false;
        for (IPlayer player1 : list) {
            if (player1.getId() == 5) {
                isPlayerExist = true;
                break;
            }
        }
        assertTrue(isPlayerExist);

        ExecuteTradeState state = newStateEmptyConstructor();
        state.removeObjectFromList(list, player);

        isPlayerExist = false;

        for (IPlayer player1 : list) {
            if (player1.getId() == 5) {
                isPlayerExist = true;
                break;
            }
        }
        assertFalse(isPlayerExist);
    }

    @Test
    public void exitTest() throws Exception {
        league = leagueFactory.newLeagueWithIdDao(1, leagueDao);
        user = userFactory.newUserWithIdDao(1, userDao);
        user.setLeague(league);
        hockeyContext.setUser(user);
        ExecuteTradeState state = newStateWithHockeyContext(hockeyContext);
        assertTrue(state.exit() instanceof AgingState);
        assertNotNull(state.exit());
    }

}