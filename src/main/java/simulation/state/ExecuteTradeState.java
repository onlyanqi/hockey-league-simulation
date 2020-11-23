package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import presentation.ReadUserInput;
import simulation.factory.ITradeOfferFactory;
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
    private ILeague league;
    private ConsoleOutput consoleOutput;
    private ReadUserInput readUserInput;
    private static Logger log = Logger.getLogger(ExecuteTradeState.class);


    public ExecuteTradeState() {
    }
    public ExecuteTradeState(IHockeyContext hockeyContext) {
        log.info("Trading check starts.");
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        consoleOutput = ConsoleOutput.getInstance();
        readUserInput = ReadUserInput.getInstance();
    }

    public ILeague getLeague() {
        return league;
    }

    public void setLeague(ILeague league) {
        this.league = league;
    }

    @Override
    public ISimulateState action() {
        loopAllTeamsForTradeInitiation(league);
        return exit();
    }

    public boolean loopAllTeamsForTradeInitiation(ILeague league) {
        boolean isValidLeague = false;
        List<IConference> conferenceList = league.getConferenceList();
        try {
            for (IConference conference : conferenceList) {
                List<IDivision> divisionList = conference.getDivisionList();
                for (IDivision division : divisionList) {
                    List<ITeam> teamList = division.getTeamList();
                    for (ITeam team : teamList) {
                        if (team.isAiTeam()) {
                            tradingLogic(team, league);
                            isValidLeague = true;
                        }
                    }
                }
            }
        } catch (Exception e){
            log.error("ExecuteTradeState: loopAllTeamsForTradeInitiation: Exception: "+e);
            throw e;
        }
        return isValidLeague;
    }

    public void tradingLogic(ITeam team, ILeague league) {
        Map<String, Object> swap = new HashMap<>();
        ITrading trading = league.getGamePlayConfig().getTrading();
        try {
            if (checkTradingPeriod(trading, league.getCurrentDate())) {
                if (checkLossPoint(team, trading)) {
                    if (checkCurrentTradeOffer(team, league)) {
                        double tradeOfferChance = getRandomDouble();
                        if (tradeOfferChance < trading.getRandomTradeOfferChance()) {
                            List<IPlayer> tradingPlayerList = getWeakestPlayerList(team, league);
                            swap.put(FROMPLAYERLISTBEFORETRADE, tradingPlayerList);
                            for (IPlayer weakestPlayer : tradingPlayerList) {
                                swap.put(FROMTEAM, team);
                                findBestSwapPlayer(team, league, weakestPlayer, swap);
                                IPlayer swapPlayer = (IPlayer) swap.get(TOPLAYER);
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
        } catch (NullPointerException e){
            log.error("ExecuteTradeState: tradingLogic: NullPointerException: "+e);
            throw e;
        } catch (Exception e){
            log.error("ExecuteTradeState: tradingLogic: Exception: "+e);
            throw e;
        }
    }

    public void createTradeOffer(Map<String, Object> swap) {
        String PENDING = "pending";
        ITeam fromTeam = (ITeam) swap.get(FROMTEAM);
        ITeam toTeam = (ITeam) swap.get(TOTEAM);
        ITrading trading = league.getGamePlayConfig().getTrading();
        ITradeOfferFactory tradeOfferConcrete = hockeyContext.getTradeOfferFactory();
        ITradeOffer tradeOffer = tradeOfferConcrete.newTradeOffer();
        try {
            tradeOffer.setLeagueId(league.getId());
            tradeOffer.setTradingId(trading.getId());
            tradeOffer.setStatus(PENDING);
            tradeOffer.setFromTeamId(fromTeam.getId());
            tradeOffer.setToTeamId(toTeam.getId());
            tradeOffer.setSeasonId(LocalDate.now().getYear());
            swap.put(TRADEOFFER, tradeOffer);
            createSwap(swap);
            if (league.getTradeOfferList() == null || league.getTradeOfferList().isEmpty()) {
                league.setTradeOfferList(new ArrayList<>());
            }
            league.getTradeOfferList().add(tradeOffer);
        } catch (NullPointerException e){
            log.error("ExecuteTradeState: createTradeOffer: NullPointerException: "+e);
            throw e;
        } catch (Exception e){
            log.error("ExecuteTradeState: createTradeOffer: Exception: "+e);
            throw e;
        }
    }

    private void createSwap(Map<String, Object> swap){
        List<IPlayer> fromPlayerListBeforeTrade = (List<IPlayer>) swap.get(FROMPLAYERLISTBEFORETRADE);
        IPlayer toPlayer = (Player) swap.get(TOPLAYER);
        ITradeOffer tradeOffer = (TradeOffer) swap.get(TRADEOFFER);
        List<IPlayer> fromPlayerListAfterTrade;
        double zero = 0;

        if(fromPlayerListBeforeTrade == null || fromPlayerListBeforeTrade.isEmpty()){
            return;
        } else {
            List<Integer> fromPlayerIdList = new ArrayList<>();
            try {
                List<Integer> toPlayerList = new ArrayList<>(Arrays.asList(toPlayer.getId()));
                tradeOffer.setToPlayerIdList(toPlayerList);
                fromPlayerListAfterTrade = new ArrayList<>();
                double fromPlayerListStrength = zero;
                for (IPlayer player : fromPlayerListBeforeTrade) {
                    fromPlayerListStrength = fromPlayerListStrength + player.getRelativeStrength();
                    if (fromPlayerListStrength < toPlayer.getRelativeStrength()) {
                        fromPlayerListAfterTrade.add(player);
                        fromPlayerIdList.add(player.getId());
                        continue;
                    } else {
                        tradeOffer.setFromPlayerIdList(fromPlayerIdList);
                        break;
                    }
                }
                swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListAfterTrade);
            } catch (NullPointerException e){
                log.error("ExecuteTradeState: createSwap: NullPointerException: "+e);
                throw e;
            } catch (Exception e){
                log.error("ExecuteTradeState: createSwap: Exception: "+e);
                throw e;
            }
        }
    }

    public void performTrade(Map<String, Object> swap) {
        ITeam toTeam = (Team) swap.get(TOTEAM);
        ITeam fromTeam = (Team) swap.get(FROMTEAM);
        fromTeam.setTradeOfferCountOfSeason(fromTeam.getTradeOfferCountOfSeason() + 1);
        fromTeam.setLossPoint(0);
        createTradeOffer(swap);
        if (toTeam.isAiTeam()) {
            if (acceptRejectTradeOffer(swap)) {
                updateTradingDetails(swap);
                consoleOutput.printMsgToConsole("Below trade is accepted successfully.");
            } else {
                ITradeOffer tradeOffer = (TradeOffer) swap.get(TRADEOFFER);
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
            ITradeOffer tradeOffer = (TradeOffer) swap.get(TRADEOFFER);
            tradeOffer.setStatus(REJECTED);

            consoleOutput.printMsgToConsole("Trade offer rejected successfully.");
        }
    }

    public boolean acceptRejectTradeOffer(Map<String, Object> tradeDetails) {
        boolean isTraded = false;
        int zero = 0;
        ITrading trading = league.getGamePlayConfig().getTrading();
        ITeam toTeam = (Team) tradeDetails.get(TOTEAM);
        IPlayer fromPlayer = (Player) tradeDetails.get(FROMPLAYER);
        IPlayer toPlayer = (Player) tradeDetails.get(TOPLAYER);
        ITeam fromTeam = (Team) tradeDetails.get(FROMTEAM);
        
        if (checkTeamStrength(toTeam, fromPlayer, toPlayer)) {
            isTraded = true;
        } else {

            double characterValue = zero;
            for(String key : trading.getGmTable().keySet()){
                if(key.equalsIgnoreCase(fromTeam.getManager().getPersonality())) {
                    characterValue = trading.getGmTable().get(key);
                    break;
                }
            }

            double tradeAcceptChance = getRandomDouble();
            if (tradeAcceptChance < (trading.getRandomAcceptanceChance() + characterValue)) {
                isTraded = true;
            }
        }
        return isTraded;
    }

    private void updateTradingDetailsInTeams(Map<String, Object> tradeDetails){
        ITeam fromTeam = (Team) tradeDetails.get(FROMTEAM);
        IPlayer toPlayer = (Player) tradeDetails.get(TOPLAYER);
        List<IPlayer> fromPlayerList = (List<IPlayer>) tradeDetails.get(FROMPLAYERLISTAFTERTRADE);
        ITeam toTeam = (Team) tradeDetails.get(TOTEAM);
        List<IPlayer> toTeamPlayerList = toTeam.getPlayerList();
        List<IPlayer> fromTeamPlayerList = fromTeam.getPlayerList();

        try {
            fromTeamPlayerList.add(toPlayer);
            toPlayer.setTeamId(fromTeam.getId());
            removeObjectFromList(toTeamPlayerList, toPlayer);

            for (IPlayer player : fromPlayerList) {
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
        }catch (NullPointerException e){
            log.error("ExecuteTradeState: updateTradingDetailsInTeams: NullPointerException: "+e);
            throw e;
        } catch (Exception e){
            log.error("ExecuteTradeState: updateTradingDetailsInTeams: Exception: "+e);
            throw e;
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

    public boolean checkTeamStrength(ITeam team, IPlayer newPlayer, IPlayer existPlayer) {
        double beforeStrength = team.getStrength();
        double afterStrength = 0;
        List<IPlayer> playerList = team.getPlayerList();
        List<IPlayer> tempPlayerList = new ArrayList<>(playerList);
        removeObjectFromList(tempPlayerList, existPlayer);
        tempPlayerList.add(newPlayer);
        for (IPlayer player : tempPlayerList) {
            afterStrength += player.getStrength();
        }
        return afterStrength >= beforeStrength;
    }

    public void findBestSwapPlayer(ITeam team, ILeague league,
                                   IPlayer weakestPlayer, Map<String, Object> swap) {
        IPlayer swapPlayer = null;
        List<IConference> conferenceList = league.getConferenceList();
        for (IConference conference : conferenceList) {
            List<IDivision> divisionList = conference.getDivisionList();
            for (IDivision division : divisionList) {
                List<ITeam> teamList = division.getTeamList();
                for (ITeam otherTeam : teamList) {
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

    public IPlayer algorithmToFindSwapPlayer(ITeam otherTeam, IPlayer weakestPlayer,
                                            IPlayer swapPlayer, Map<String, Object> swap) {

        List<IPlayer> playerList = otherTeam.getPlayerList();
        for (IPlayer player : playerList) {
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

    public List<IPlayer> getWeakestPlayerList(ITeam team, ILeague league) {
        List<IPlayer> playerList = team.getPlayerList();
        List<IPlayer> returnPlayerList = new ArrayList<>();
        Collections.sort(playerList);
        for (IPlayer player : playerList) {
            if (returnPlayerList.size() < league.getGamePlayConfig().getTrading().getMaxPlayersPerTrade()) {
                returnPlayerList.add(player);
            } else {
                break;
            }
        }
        return returnPlayerList;
    }

    public boolean checkCurrentTradeOffer(ITeam team, ILeague league) {
        boolean isTradingAllowed = false;

        ITrading trading = league.getGamePlayConfig().getTrading();
        if (team.getTradeOfferCountOfSeason() < trading.getMaxPlayersPerTrade()) {
            isTradingAllowed = true;
        }

        return isTradingAllowed;
    }

    public boolean checkTradingPeriod(ITrading trading, LocalDate currentDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
        trading.isLeagueInTradingPeriod(date);
        return trading.isTradingPeriod();
    }

    public boolean checkLossPoint(ITeam team, ITrading trading) {
        boolean isTradingNeeded = false;

        if (team.getLossPoint() >= trading.getLossPoint()) {
            isTradingNeeded = true;
        }

        return isTradingNeeded;
    }

    public double getRandomDouble() {
        return random.nextDouble();
    }

    public void removeObjectFromList(List<IPlayer> list, IPlayer toRemove) {

        Iterator<IPlayer> itr = list.iterator();
        while (itr.hasNext()) {
            IPlayer player = itr.next();
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
        log.info("Trading check ends.");
        return new AgingState(hockeyContext);
    }

}
