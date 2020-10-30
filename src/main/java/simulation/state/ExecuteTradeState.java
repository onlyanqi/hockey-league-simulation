package simulation.state;

import simulation.factory.TradeOfferConcrete;
import simulation.model.*;
import userIO.ConsoleOutput;
import userIO.IConsoleOutput;
import userIO.IReadUserInput;
import userIO.ReadUserInput;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ExecuteTradeState implements ISimulateState {

    private HockeyContext hockeyContext;
    private League league;

    public ExecuteTradeState(){}

    public ExecuteTradeState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    private final String PENDING = "pending";
    private final String ACCEPTED = "accepted";
    private final String FROMPLAYER = "fromPlayer";
    private final String TOPLAYER = "toPlayer";
    private final String FROMTEAM = "fromTeam";
    private final String TOTEAM = "toTeam";
    private final String TRADEOFFER = "tradeOffer";
    private final String TRADING = "trading";
    private final Random random = new Random();


    @Override
    public ISimulateState action() {
        System.out.println("Trading Players");
        loopAllTeamsForTradeInitiation(league);
        return exit();
    }

    public boolean isListNotEmpty(List list){
        boolean isNotEmpty = true;

        if(list == null || list.isEmpty()){
            isNotEmpty = false;
        }

        return isNotEmpty;
    }

    public boolean isNotNull(Object input){
        boolean isNotNull = true;

        if(input == null){
            isNotNull = false;
        }

        return isNotNull;
    }

    public boolean loopAllTeamsForTradeInitiation(League league){
        boolean isValidLeague = false;
        if(isNotNull(league)){
            List<Conference> conferenceList = league.getConferenceList();
            if (isListNotEmpty(conferenceList)) {
                for (Conference conference : conferenceList) {
                    List<Division> divisionList = conference.getDivisionList();
                    if (isListNotEmpty(divisionList)) {
                        for (Division division : divisionList) {
                            List<Team> teamList = division.getTeamList();
                            if (isListNotEmpty(teamList)) {
                                for (Team team : teamList) {
                                    if(team.isAiTeam()) {
                                        tradingLogic(team, league);
                                        isValidLeague = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return isValidLeague;
    }

    public void tradingLogic(Team team, League league){
        Map<String, Object> swap = new HashMap<>();
        Trading trading = league.getGamePlayConfig().getTrading();
        if(checkTradingPeriod(trading, league.getCurrentDate())){
            System.out.println("team name: "+team.getName()+" loss point: "+team.getLossPoint());
            if(checkLossPoint(team, trading)){
                if(checkCurrentTradeOffer(team, league)) {
                    double tradeOfferChance = getRandomDouble();
                    if (tradeOfferChance < trading.getRandomTradeOfferChance()) {
                        List<Player> tradingPlayerList = getWeakestPlayerList(team, league);
                        if(isListNotEmpty(tradingPlayerList)) {
                            for(Player weakestPlayer : tradingPlayerList) {
                                swap.put(FROMPLAYER, weakestPlayer);
                                swap.put(FROMTEAM, team);
                                findBestSwapPlayer(team, league, weakestPlayer, swap);
                                Player swapPlayer = (Player) swap.get(TOPLAYER);
                                if (isNotNull(swapPlayer)) {
                                    createTradeOffer(swap);
                                    performTrade(swap);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void createTradeOffer(Map<String, Object> swap){
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
        if(league.getTradingOfferList() == null || league.getTradingOfferList().isEmpty()){
            league.setTradingOfferList(new ArrayList<>());
        }
        league.getTradingOfferList().add(tradeOffer);
        swap.put(TRADEOFFER, tradeOffer);
        swap.put(TRADING, trading);
    }

    public void performTrade(Map<String, Object> swap){
        Team toTeam = (Team) swap.get(TOTEAM);
        if(toTeam.isAiTeam()) {
            if (acceptRejectTradeOffer(swap)) {
                updateTradingDetails(swap);
                ConsoleOutput.printToConsole("Below trade is accepted successfully.");
                printTradeDetailsToUser(swap);
            } else {
                ConsoleOutput.printToConsole("Below trade is rejected.");
                printTradeDetailsToUser(swap);
            }
        } else {
            performUserTrade(swap);
        }
    }

    public void performUserTrade(Map<String, Object> swap){
        printTradeDetailsToUser(swap);
        String userResponse = readUserInput(swap);
        if(userResponse.equals("A".trim()) || userResponse.equals("a".trim())){
            updateTradingDetails(swap);
        } else {
            ConsoleOutput.printToConsole("Trade offer rejected successfully.");
        }
    }

    public void printTradeDetailsToUser(Map<String, Object> swap){
        Player fromPlayer = (Player) swap.get(FROMPLAYER);
        Player toPlayer = (Player) swap.get(TOPLAYER);
        Team fromTeam = (Team) swap.get(FROMTEAM);
        Team toTeam = (Team) swap.get(TOTEAM);

        ConsoleOutput.printToConsole("Below Trade Offer is received:");
        ConsoleOutput.printToConsole("----------------------------------");
        ConsoleOutput.printToConsole("\tFrom team: "+fromTeam.getName());
        ConsoleOutput.printToConsole("\t\tPlayer name: "+fromPlayer.getName());
        ConsoleOutput.printToConsole("\t\tPlayer strength: "+fromPlayer.getStrength());
        ConsoleOutput.printToConsole("\tTo Team name: "+toTeam.getName());
        ConsoleOutput.printToConsole("\t\tPlayer name: "+toPlayer.getName());
        ConsoleOutput.printToConsole("\t\tPlayer strength: "+toPlayer.getStrength());
        ConsoleOutput.printToConsole("----------------------------------");
    }

    public String readUserInput(Map<String, Object> swap){
        String userResponse;
        boolean isValid;
        userResponse = ReadUserInput.getUserInput("Enter \"A\" to accept or \"R\" to reject the trade offer.");
        do {
            if(userResponse == null || userResponse.equals("".trim())){
                userResponse = ReadUserInput.getUserInput("Kindly check the input. " +
                        "Enter \"A\" to accept or \"R\" to reject the trade offer.");
                isValid = true;
            } else if(userResponse.equals("A".trim()) && userResponse.equals("R".trim())
                    && userResponse.equals("a".trim()) && userResponse.equals("r".trim())){

                isValid = false;
            } else {
                userResponse = ReadUserInput.getUserInput("Kindly check the input. " +
                        "Enter \"A\" to accept or \"R\" to reject the trade offer.");
                isValid = true;
            }
        } while(isValid);

        return userResponse;
    }

    public boolean acceptRejectTradeOffer(Map<String, Object> tradeDetails){
        boolean isTraded = false;
        if(isNotNull(tradeDetails)) {
            Trading trading = (Trading) tradeDetails.get(TRADING);
            if(isNotNull(trading)) {
                Team toTeam = (Team) tradeDetails.get(TOTEAM);
                Player fromPlayer = (Player) tradeDetails.get(FROMPLAYER);
                Player toPlayer = (Player) tradeDetails.get(TOPLAYER);
                if (checkTeamStrength(toTeam, fromPlayer, toPlayer)) {
                    isTraded = true;
                } else {
                    double tradeAcceptChance = getRandomDouble();
                    if (isNotNull(trading) && tradeAcceptChance < trading.getRandomAcceptanceChance()) {
                        isTraded = true;
                    }
                }
            }
        }
        return isTraded;
    }

    public void updateTradingDetails(Map<String, Object> tradeDetails){
        Team fromTeam = (Team) tradeDetails.get(FROMTEAM);
        Team toTeam = (Team) tradeDetails.get(TOTEAM);
        TradeOffer tradeOffer = (TradeOffer) tradeDetails.get(TRADEOFFER);
        Player fromPlayer = (Player) tradeDetails.get(FROMPLAYER);
        Player toPlayer = (Player) tradeDetails.get(TOPLAYER);

        fromTeam.setLossPoint(0);
        tradeOffer.setStatus(ACCEPTED);
        List<Player> fromPlayerList = fromTeam.getPlayerList();
        fromPlayerList.remove(fromPlayer);
        fromPlayerList.add(toPlayer);

        List<Player> toPlayerList = toTeam.getPlayerList();
        toPlayerList.remove(toPlayer);
        toPlayerList.add(fromPlayer);

        if(fromPlayer.isCaptain()){
            Collections.sort(fromPlayerList, Collections.reverseOrder());
            fromPlayerList.get(0).setCaptain(true);
        }
        if(toPlayer.isCaptain()){
            Collections.sort(toPlayerList, Collections.reverseOrder());
            toPlayerList.get(0).setCaptain(true);
        }
    }

    public boolean checkTeamStrength(Team team, Player newPlayer, Player existPlayer){
        double beforeStrength = team.getStrength();
        double afterStrength = 0;
        List<Player> playerList = team.getPlayerList();

        if(isListNotEmpty(playerList)) {
            List<Player> tempPlayerList = new ArrayList<>(playerList);
            removeObjectFromList(tempPlayerList, existPlayer);
            tempPlayerList.add(newPlayer);
            for (Player player : tempPlayerList) {
                afterStrength += player.getStrength();
            }
        }

        return afterStrength >= beforeStrength;
    }



    public void findBestSwapPlayer(Team team, League league,
                                                  Player weakestPlayer, Map<String, Object> swap){
        Player swapPlayer = null;
        if(isNotNull(league)){
            List<Conference> conferenceList = league.getConferenceList();
            if(isListNotEmpty(conferenceList)){
                for(Conference conference : conferenceList){
                    List<Division> divisionList = conference.getDivisionList();
                    if(isListNotEmpty(divisionList)){
                        for(Division division : divisionList){
                            List<Team> teamList = division.getTeamList();
                            if(isListNotEmpty(teamList)){
                                for(Team otherTeam : teamList){
                                    if(team.getName().equals(otherTeam.getName())) {
                                        continue;
                                    } else{
                                        if (checkCurrentTradeOffer(otherTeam, league)) {
                                            swapPlayer = algorithmToFindSwapPlayer(otherTeam,
                                                    weakestPlayer, swapPlayer, swap);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Player algorithmToFindSwapPlayer(Team otherTeam, Player weakestPlayer,
                                            Player swapPlayer, Map<String, Object> swap){

        List<Player> playerList = otherTeam.getPlayerList();
        if(isListNotEmpty(playerList)){
            for(Player player:playerList) {
                if (isNotNull(weakestPlayer.getPosition()) && isNotNull(player.getPosition()) &&
                        (weakestPlayer.getPosition().equals(player.getPosition()))
                        && ((swapPlayer == null) || (swapPlayer.getStrength() < player.getStrength() ))) {

                    swapPlayer = player;
                    swap.put(TOPLAYER, player);
                    swap.put(TOTEAM, otherTeam);
                }
            }
        }
        return swapPlayer;
    }

    public List<Player> getWeakestPlayerList(Team team, League league){
        List<Player> playerList = team.getPlayerList();
        List<Player> returnPlayerList = null;
        if(isListNotEmpty(playerList)) {
            Collections.sort(playerList);
            returnPlayerList = new ArrayList<>();
            if (isListNotEmpty(playerList)) {
                for (Player player : playerList) {
                    if (returnPlayerList.size() < league.getGamePlayConfig().getTrading().getMaxPlayersPerTrade()) {
                        returnPlayerList.add(player);
                    } else {
                        break;
                    }
                }
            }
        }
        return returnPlayerList;
    }

    public boolean checkCurrentTradeOffer(Team team, League league){
        boolean isTradingAllowed = false;
        List<TradeOffer> tradeOfferList = league.getTradingOfferList();
        if(isListNotEmpty(tradeOfferList)){
            for(TradeOffer tradeOffer : tradeOfferList){
                if(team.getId() == tradeOffer.getFromTeamId()){
                    team.setPendingTradeOfferCount(team.getPendingTradeOfferCount()+1);
                }
            }
            Trading trading = league.getGamePlayConfig().getTrading();
            if(team.getPendingTradeOfferCount() < trading.getMaxPlayersPerTrade()){
                isTradingAllowed = true;
            }
        } else{
            isTradingAllowed = true;
        }

        return isTradingAllowed;
    }

    public boolean checkTradingPeriod(Trading trading, LocalDate currentDate){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
        trading.isLeagueInTradingPeriod(date);
        return trading.isTradingPeriod();
    }

    public boolean checkLossPoint(Team team, Trading trading){
        boolean isTradingNeeded = false;

        if(team.getLossPoint() >= trading.getLossPoint()){
            isTradingNeeded = true;
        }

        return isTradingNeeded;
    }

    public double getRandomDouble(){
        return random.nextDouble();
    }

    public void removeObjectFromList(List list, Player toRemove){

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
        return new AgingState(hockeyContext);
    }
}
