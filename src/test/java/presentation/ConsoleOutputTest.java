package presentation;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.*;
import simulation.factory.HockeyContextConcreteMock;
import simulation.factory.IHockeyContextFactory;
import simulation.model.*;
import simulation.state.IHockeyContext;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConsoleOutputTest {

    private static final String FROMTEAM = "fromTeam";
    private static final String TOTEAM = "toTeam";
    private static final String FROMPLAYERLISTAFTERTRADE = "fromPlayerListAfterTrade";
    private static ILeagueDao leagueDao;
    private static ITeamDao teamDao;
    private static IPlayerDao playerDao;
    private static ITradeOfferDao tradeOfferDao;
    private static ITradingDao tradingDao;
    private static IUserDao userDao;
    private static IModelFactory modelFactory;
    private static IDaoFactory daoFactory;
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;
    private static ILeague league;
    private static IUser user;
    private final String TOPLAYERLIST = "toPlayerList";

    @BeforeClass
    public static void init() {
        hockeyContextFactory = HockeyContextConcreteMock.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        modelFactory = hockeyContext.getModelFactory();
        daoFactory = hockeyContext.getDaoFactory();
        leagueDao = daoFactory.createLeagueDao();
        userDao = daoFactory.createUserDao();
        teamDao = daoFactory.createTeamDao();
        playerDao = daoFactory.createPlayerDao();
        tradingDao = daoFactory.createTradingDao();
        tradeOfferDao = daoFactory.createTradeOfferDao();
    }

    @Test
    public void getInstanceTest(){
        IConsoleOutput consoleOutput = ConsoleOutput.getInstance();
        assertTrue(consoleOutput instanceof IConsoleOutput);
    }

    @Test
    public void printTradeDetailsToUserTest() throws Exception {
        Map<String, Object> swap = new HashMap<>();

        IPlayer player1 = modelFactory.createPlayerWithIdDao(3, playerDao);
        IPlayer player2 = modelFactory.createPlayerWithIdDao(1, playerDao);
        ITeam team1 = modelFactory.createTeamWithIdDao(1, teamDao);
        ITeam team2 = modelFactory.createTeamWithIdDao(3, teamDao);
        ITrading trading = modelFactory.createTradingWithIdDao(3, tradingDao);
        league = modelFactory.createLeagueWithIdDao(1, leagueDao);
        league.getGamePlayConfig().setTrading(trading);
        user = modelFactory.createUserWithIdDao(1, userDao);
        user.setLeague(league);
        hockeyContext.setUser(user);
        List<IPlayer> fromPlayerListAfterTrade = new ArrayList<>(Arrays.asList(player1));
        List<IPlayer> toPlayerList = new ArrayList<>(Arrays.asList(player2));

        swap.put(TOPLAYERLIST, toPlayerList);
        swap.put(FROMTEAM, team1);
        swap.put(TOTEAM, team2);
        swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListAfterTrade);

        IConsoleOutput consoleOutput = ConsoleOutput.getInstance();
        consoleOutput.printTradeDetailsToUser(swap);

        assertNotNull(swap);
    }

}
