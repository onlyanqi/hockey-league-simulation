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
    private final String FROMPLAYERLISTBEFORETRADE = "fromPlayerListBeforeTrade";
    private final String FROMPLAYERLISTAFTERTRADE = "fromPlayerListAfterTrade";
    private final String TOPLAYER = "toPlayer";
    private final String FROMTEAM = "fromTeam";
    private final String TOTEAM = "toTeam";
    private final String TRADEOFFER = "tradeOffer";
    private final Random random = new Random();
    private final String REJECTED = "rejected";
    private IHockeyContext hockeyContext;
    private League league;
    private ConsoleOutput consoleOutput;
    private ReadUserInput readUserInput;
    public ExecuteTradeState() {
    }
    public ExecuteTradeState(IHockeyContext hockeyContext) {
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
                        swap.put(FROMPLAYERLISTBEFORETRADE, tradingPlayerList);
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
        Team fromTeam = (Team) swap.get(FROMTEAM);
        Team toTeam = (Team) swap.get(TOTEAM);
        Trading trading = league.getGamePlayConfig().getTrading();
        TradeOfferConcrete tradeOfferConcrete = new TradeOfferConcrete();
        TradeOffer tradeOffer = tradeOfferConcrete.newTradeOffer();
        tradeOffer.setLeagueId(league.getId());
        tradeOffer.setTradingId(trading.getId());
        tradeOffer.setStatus(PENDING);
        tradeOffer.setFromTeamId(fromTeam.getId());
        tradeOffer.setToTeamId(toTeam.getId());
        createSwap(swap);
        tradeOffer.setSeasonId(LocalDate.now().getYear());
        if (league.getTradeOfferList() == null || league.getTradeOfferList().isEmpty()) {
            league.setTradeOfferList(new ArrayList<>());
        }
        league.getTradeOfferList().add(tradeOffer);
        swap.put(TRADEOFFER, tradeOffer);
    }

    private void createSwap(Map<String, Object> swap){
        List<Player> fromPlayerListBeforeTrade = (List<Player>) swap.get(FROMPLAYERLISTBEFORETRADE);
        Player toPlayer = (Player) swap.get(TOPLAYER);
        TradeOffer tradeOffer = (TradeOffer) swap.get(TRADEOFFER);
        List<Player> fromPlayerListAfterTrade;
        double zero = 0;


        if(fromPlayerListBeforeTrade == null || fromPlayerListBeforeTrade.isEmpty()){
            return;
        } else {
            List<Integer> fromPlayerIdList = new ArrayList<>();
            List<Integer> toPlayerList = new ArrayList<>(Arrays.asList(toPlayer.getId()));
            tradeOffer.setToPlayerIdList(toPlayerList);
            fromPlayerListAfterTrade = new ArrayList<>();
            double fromPlayerListStrength = zero;
            for(Player player : fromPlayerListBeforeTrade){
                fromPlayerListStrength = fromPlayerListStrength + player.getRelativeStrength();
                if(fromPlayerListStrength < toPlayer.getRelativeStrength()){
                    fromPlayerListAfterTrade.add(player);
                    fromPlayerIdList.add(player.getId());
                    continue;
                } else {
                    tradeOffer.setFromPlayerIdList(fromPlayerIdList);
                    break;
                }
            }
            swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListAfterTrade);
        }
    }

    public void performTrade(Map<String, Object> swap) {
        Team toTeam = (Team) swap.get(TOTEAM);
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
        int zero = 0;
        Trading trading = league.getGamePlayConfig().getTrading();
        Team toTeam = (Team) tradeDetails.get(TOTEAM);
        Player fromPlayer = (Player) tradeDetails.get(FROMPLAYER);
        Player toPlayer = (Player) tradeDetails.get(TOPLAYER);
        
        if (checkTeamStrength(toTeam, fromPlayer, toPlayer)) {
            isTraded = true;
        } else {

            int count = zero;
            double characterTotal = zero;
            for(String key : trading.getGmTable().keySet()){
                double keyValue = trading.getGmTable().get(key);
                characterTotal = characterTotal + keyValue;
                count = count + 1;
            }

            double characterTradeInfluence = characterTotal/count;

            double tradeAcceptChance = getRandomDouble();
            if (tradeAcceptChance < (trading.getRandomAcceptanceChance() + characterTradeInfluence)) {
                isTraded = true;
            }
        }
        return isTraded;
    }

    private void updateTradingDetailsInTeams(Map<String, Object> tradeDetails){
        Team fromTeam = (Team) tradeDetails.get(FROMTEAM);
        Player toPlayer = (Player) tradeDetails.get(TOPLAYER);
        List<Player> fromPlayerList = (List<Player>) tradeDetails.get(FROMPLAYERLISTAFTERTRADE);
        Team toTeam = (Team) tradeDetails.get(TOTEAM);
        List<Player> toTeamPlayerList = toTeam.getPlayerList();

        List<Player> fromTeamPlayerList = fromTeam.getPlayerList();
        fromTeamPlayerList.add(toPlayer);
        toPlayer.setTeamId(fromTeam.getId());
        removeObjectFromList(toTeamPlayerList, toPlayer);

        for(Player player : fromPlayerList) {
            removeObjectFromList(fromTeamPlayerList, player);
            toTeamPlayerList.add(player);
            player.setTeamId(toTeam.getId());
            if (player.isCaptain()) {
                player.setCaptain(false);
                Collections.sort(fromTeamPlayerList, Collections.reverseOrder());
                fromTeamPlayerList.get(0).setCaptain(true);
            }
        }

        if (toPlayer.isCaptain()) {
            toPlayer.setCaptain(false);
            Collections.sort(toTeamPlayerList, Collections.reverseOrder());
            toTeamPlayerList.get(0).setCaptain(true);
        }
    }

    public void updateTradingDetails(Map<String, Object> tradeDetails) {
        String ACCEPTED = "accepted";
        Team fromTeam = (Team) tradeDetails.get(FROMTEAM);
        Team toTeam = (Team) tradeDetails.get(TOTEAM);
        TradeOffer tradeOffer = (TradeOffer) tradeDetails.get(TRADEOFFER);

        updateTradingDetailsInTeams(tradeDetails);
        fromTeam.fixTeamAfterTrading(league.getFreeAgent().getPlayerList());
        toTeam.fixTeamAfterTrading(league.getFreeAgent().getPlayerList());

        tradeOffer.setStatus(ACCEPTED);

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
            if ((weakestPlayer.getRelativeStrength() < player.getRelativeStrength()) &&
                    ((swapPlayer == null) || (swapPlayer.getRelativeStrength() < player.getRelativeStrength()))) {

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
