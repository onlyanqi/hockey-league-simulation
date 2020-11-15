package simulation.state;

import presentation.ConsoleOutput;
import presentation.ReadUserInput;
import simulation.factory.TradeOfferConcrete;
import simulation.model.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ExecuteTradeState implements ISimulateState {

    private final String FROMPLAYER = "fromPlayer";
    private final String TOPLAYER = "toPlayer";
    private final String FROMTEAM = "fromTeam";
    private final String TOTEAM = "toTeam";
    private final String TRADEOFFER = "tradeOffer";
    private final String TRADING = "trading";
    private final Random random = new Random();
    private final String REJECTED = "rejected";
    private HockeyContext hockeyContext;
    private League league;
    private ConsoleOutput consoleOutput;
    private ReadUserInput readUserInput;
    public ExecuteTradeState() {
    }
    public ExecuteTradeState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        consoleOutput = ConsoleOutput.getInstance();
        readUserInput = ReadUserInput.getInstance();
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public ISimulateState action() {
        loopAllTeamsForTradeInitiation(league);
        return exit();
    }

    public boolean loopAllTeamsForTradeInitiation(League league) {
        boolean isValidLeague = false;
        List<Conference> conferenceList = league.getConferenceList();
        for (Conference conference : conferenceList) {
            List<Division> divisionList = conference.getDivisionList();
            for (Division division : divisionList) {
                List<Team> teamList = division.getTeamList();
                for (Team team : teamList) {
                    if (team.isAiTeam()) {
                        tradingLogic(team, league);
                        isValidLeague = true;
                    }
                }
            }
        }
        return isValidLeague;
    }

    public void tradingLogic(Team team, League league) {
        Map<String, Object> swap = new HashMap<>();
        Trading trading = league.getGamePlayConfig().getTrading();
        if (checkTradingPeriod(trading, league.getCurrentDate())) {
            if (checkLossPoint(team, trading)) {
                if (checkCurrentTradeOffer(team, league)) {
                    double tradeOfferChance = getRandomDouble();
                    if (tradeOfferChance < trading.getRandomTradeOfferChance()) {
                        List<Player> tradingPlayerList = getWeakestPlayerList(team, league);

                        for (Player weakestPlayer : tradingPlayerList) {
                            swap.put(FROMTEAM, team);
                            findBestSwapPlayer(team, league, weakestPlayer, swap);
                            Player swapPlayer = (Player) swap.get(TOPLAYER);
                            if (swapPlayer == null) {
                                continue;
                            } else {
                                performTrade(swap);
                            }
                        }
                    }
                }
            }
        }
    }

    public void createTradeOffer(Map<String, Object> swap) {
        String PENDING = "pending";
        Trading trading = league.getGamePlayConfig().getTrading();
        Player weakestPlayer = (Player) swap.get(FROMPLAYER);
        Player swapPlayer = (Player) swap.get(TOPLAYER);
        TradeOfferConcrete tradeOfferConcrete = new TradeOfferConcrete();
        TradeOffer tradeOffer = tradeOfferConcrete.newTradeOffer();
        tradeOffer.setLeagueId(league.getId());
        tradeOffer.setTradingId(trading.getId());
        tradeOffer.setStatus(PENDING);
        tradeOffer.setFromPlayerId(weakestPlayer.getId());
        tradeOffer.setFromTeamId(weakestPlayer.getTeamId());
        tradeOffer.setToTeamId(swapPlayer.getTeamId());
        tradeOffer.setToPlayerId(swapPlayer.getId());
        tradeOffer.setSeasonId(LocalDate.now().getYear());
        if (league.getTradeOfferList() == null || league.getTradeOfferList().isEmpty()) {
            league.setTradeOfferList(new ArrayList<>());
        }
        league.getTradeOfferList().add(tradeOffer);
        swap.put(TRADEOFFER, tradeOffer);
    }

    public void performTrade(Map<String, Object> swap) {
        Team toTeam = (Team) swap.get(TOTEAM);
        Trading trading = league.getGamePlayConfig().getTrading();
        swap.put(TRADING, trading);
        Team fromTeam = (Team) swap.get(FROMTEAM);
        fromTeam.setTradeOfferCountOfSeason(fromTeam.getTradeOfferCountOfSeason() + 1);
        fromTeam.setLossPoint(0);
        createTradeOffer(swap);
        if (toTeam.isAiTeam()) {
            if (acceptRejectTradeOffer(swap)) {
                updateTradingDetails(swap);
                consoleOutput.printMsgToConsole("Below trade is accepted successfully.");
            } else {
                TradeOffer tradeOffer = (TradeOffer) swap.get(TRADEOFFER);
                tradeOffer.setStatus(REJECTED);
                consoleOutput.printMsgToConsole("Below trade is rejected.");
            }
            consoleOutput.printAITradeDetailsToUser(swap);
        } else {
            performUserTrade(swap);
        }
    }

    public void performUserTrade(Map<String, Object> swap) {
        consoleOutput.printUserTradeDetailsToUser(swap);
        String userResponse = readUserInput.getUserTradeResponse();
        String a = "A";
        if (userResponse.equalsIgnoreCase(a.trim())) {
            updateTradingDetails(swap);
            consoleOutput.printMsgToConsole("Trade offer is accepted successfully.");
        } else {
            TradeOffer tradeOffer = (TradeOffer) swap.get(TRADEOFFER);
            tradeOffer.setStatus(REJECTED);

            consoleOutput.printMsgToConsole("Trade offer rejected successfully.");
        }
    }

    public boolean acceptRejectTradeOffer(Map<String, Object> tradeDetails) {
        boolean isTraded = false;
        Trading trading = (Trading) tradeDetails.get(TRADING);
        Team toTeam = (Team) tradeDetails.get(TOTEAM);
        Player fromPlayer = (Player) tradeDetails.get(FROMPLAYER);
        Player toPlayer = (Player) tradeDetails.get(TOPLAYER);
        if (checkTeamStrength(toTeam, fromPlayer, toPlayer)) {
            isTraded = true;
        } else {
            double tradeAcceptChance = getRandomDouble();
            if (tradeAcceptChance < trading.getRandomAcceptanceChance()) {
                isTraded = true;
            }
        }
        return isTraded;
    }

    public void updateTradingDetails(Map<String, Object> tradeDetails) {
        String ACCEPTED = "accepted";
        Team fromTeam = (Team) tradeDetails.get(FROMTEAM);
        Team toTeam = (Team) tradeDetails.get(TOTEAM);
        TradeOffer tradeOffer = (TradeOffer) tradeDetails.get(TRADEOFFER);
        Player fromPlayer = (Player) tradeDetails.get(FROMPLAYER);
        Player toPlayer = (Player) tradeDetails.get(TOPLAYER);

        tradeOffer.setStatus(ACCEPTED);
        List<Player> fromPlayerList = fromTeam.getPlayerList();
        fromPlayerList.remove(fromPlayer);
        fromPlayerList.add(toPlayer);

        List<Player> toPlayerList = toTeam.getPlayerList();
        toPlayerList.remove(toPlayer);
        toPlayerList.add(fromPlayer);

        fromPlayer.setTeamId(toTeam.getId());
        toPlayer.setTeamId(fromTeam.getId());

        boolean valid = fromTeam.validTeam();
        if (valid) {
            consoleOutput.printMsgToConsole("Validated seller team after trading.");
        }

        valid = toTeam.validTeam();
        if (valid) {
            consoleOutput.printMsgToConsole("Validated buyer team after trading.");
        }

        if (fromPlayer.isCaptain()) {
            Collections.sort(fromPlayerList, Collections.reverseOrder());
            fromPlayerList.get(0).setCaptain(true);
        }
        if (toPlayer.isCaptain()) {
            Collections.sort(toPlayerList, Collections.reverseOrder());
            toPlayerList.get(0).setCaptain(true);
        }
    }

    public boolean checkTeamStrength(Team team, Player newPlayer, Player existPlayer) {
        double beforeStrength = team.getStrength();
        double afterStrength = 0;
        List<Player> playerList = team.getPlayerList();
        List<Player> tempPlayerList = new ArrayList<>(playerList);
        removeObjectFromList(tempPlayerList, existPlayer);
        tempPlayerList.add(newPlayer);
        for (Player player : tempPlayerList) {
            afterStrength += player.getStrength();
        }
        return afterStrength >= beforeStrength;
    }

    public Boolean checkDayOne() {
        if (league.getCurrentDate().equals(LocalDate.of(LocalDate.now().getYear(), 10, 1))) {
            return false;
        } else {
            return true;
        }
    }

    public void findBestSwapPlayer(Team team, League league,
                                   Player weakestPlayer, Map<String, Object> swap) {
        Player swapPlayer = null;
        List<Conference> conferenceList = league.getConferenceList();
        for (Conference conference : conferenceList) {
            List<Division> divisionList = conference.getDivisionList();
            for (Division division : divisionList) {
                List<Team> teamList = division.getTeamList();
                for (Team otherTeam : teamList) {
                    if (team.getName().equals(otherTeam.getName())) {
                        continue;
                    } else {
                        if (checkCurrentTradeOffer(otherTeam, league)) {
                            swapPlayer = algorithmToFindSwapPlayer(otherTeam,
                                    weakestPlayer, swapPlayer, swap);
                        }
                    }
                }
            }
        }
    }

    public Player algorithmToFindSwapPlayer(Team otherTeam, Player weakestPlayer,
                                            Player swapPlayer, Map<String, Object> swap) {

        List<Player> playerList = otherTeam.getPlayerList();
        for (Player player : playerList) {
            if ((weakestPlayer.getPosition().equals(player.getPosition()))
                    && ((swapPlayer == null) || (swapPlayer.getStrength() < player.getStrength()))) {

                swapPlayer = player;
                swap.put(TOPLAYER, player);
                swap.put(TOTEAM, otherTeam);
                swap.put(FROMPLAYER, weakestPlayer);
            }
        }
        return swapPlayer;
    }

    public List<Player> getWeakestPlayerList(Team team, League league) {
        List<Player> playerList = team.getPlayerList();
        List<Player> returnPlayerList = new ArrayList<>();
        Collections.sort(playerList);
        for (Player player : playerList) {
            if (returnPlayerList.size() < league.getGamePlayConfig().getTrading().getMaxPlayersPerTrade()) {
                returnPlayerList.add(player);
            } else {
                break;
            }
        }
        return returnPlayerList;
    }

    public boolean checkCurrentTradeOffer(Team team, League league) {
        boolean isTradingAllowed = false;

        Trading trading = league.getGamePlayConfig().getTrading();
        if (team.getTradeOfferCountOfSeason() < trading.getMaxPlayersPerTrade()) {
            isTradingAllowed = true;
        }

        return isTradingAllowed;
    }

    public boolean checkTradingPeriod(Trading trading, LocalDate currentDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
        trading.isLeagueInTradingPeriod(date);
        return trading.isTradingPeriod();
    }

    public boolean checkLossPoint(Team team, Trading trading) {
        boolean isTradingNeeded = false;

        if (team.getLossPoint() >= trading.getLossPoint()) {
            isTradingNeeded = true;
        }

        return isTradingNeeded;
    }

    public double getRandomDouble() {
        return random.nextDouble();
    }

    public void removeObjectFromList(List<Player> list, Player toRemove) {

        Iterator<Player> itr = list.iterator();
        while (itr.hasNext()) {
            Player player = itr.next();
            if (player.getName().equals(toRemove.getName())
                    && player.getPosition().equals(toRemove.getPosition())
                    && player.getStrength() == toRemove.getStrength()
                    && player.getSkating() == toRemove.getSkating()) {

                itr.remove();
                break;
            }
        }
    }

    public ISimulateState exit() {
        hockeyContext.getUser().setLeague(league);
        return new AgingState(hockeyContext);
    }

}
