package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import presentation.ReadUserInput;
import simulation.factory.ITradeOfferFactory;
import simulation.model.IPlayer;
import simulation.model.ITeam;
import simulation.model.IConference;
import simulation.model.IDivision;
import simulation.model.ILeague;
import simulation.model.ITrading;
import simulation.model.ITradeOffer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ExecuteTradeState implements ISimulateState {

    private final String FROMPLAYERLISTBEFORETRADE = "fromPlayerListBeforeTrade";
    private final String FROMPLAYERLISTAFTERTRADE = "fromPlayerListAfterTrade";
    private final String FROMTEAM = "fromTeam";
    private final String TOTEAM = "toTeam";
    private final String TRADEOFFER = "tradeOffer";
    private final Random random = new Random();
    private final String REJECTED = "rejected";
    private final Integer[] tradedDraftRound = {1, 2, 3, 4, 5, 6, 7};
    private final int ZERO = 0;
    private final String TRADED_ROUND_NUMBER = "tradedRoundNumber";
    private final String TOPLAYERLIST = "toPlayerList";
    private ITrading trading = null;
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
        trading = league.getGamePlayConfig().getTrading();
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
        try {
            if (checkTradingPeriod(trading, league.getCurrentDate())) {
                if (checkLossPoint(team, trading)) {
                    if (checkCurrentTradeOffer(team)) {
                        double tradeOfferChance = getRandomDouble();
                        if (tradeOfferChance < trading.getRandomTradeOfferChance()) {
                            List<IPlayer> tradingPlayerList = getWeakestPlayerList(team, league);
                            swap.put(FROMPLAYERLISTBEFORETRADE, tradingPlayerList);
                            Collections.sort(tradingPlayerList);
                            for (IPlayer weakestPlayer : tradingPlayerList) {
                                swap.put(FROMTEAM, team);
                                findBestSwapPlayer(team, league, weakestPlayer, swap);
                                List<IPlayer> swapPlayerList = (List<IPlayer>) swap.get(TOPLAYERLIST);
                                if (swapPlayerList == null || swapPlayerList.isEmpty()) {
                                    continue;
                                } else {
                                    performTrade(swap);
                                }
                                if(team.getPlayersTradedCount() >= trading.getMaxPlayersPerTrade()){
                                    team.setPlayersTradedCount(ZERO);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (ConcurrentModificationException e){
            e.printStackTrace();
            log.error("ExecuteTradeState: tradingLogic: ConcurrentModificationException: "+e);
            throw e;
        }catch (NullPointerException e){
            e.printStackTrace();
            log.error("ExecuteTradeState: tradingLogic: NullPointerException: "+e);
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            log.error("ExecuteTradeState: tradingLogic: Exception: "+e);
            throw e;
        }
    }

    private boolean strongWeakPlayerTradeDecider(){
        double half = 0.5;
        double strongWeakPlayerDecider = getRandomDouble();
        boolean getStrongPlayerFlag = false;

        if(half < strongWeakPlayerDecider){
            getStrongPlayerFlag = true;
        }
        return getStrongPlayerFlag;
    }

    private boolean tradePlayerCountDecider(){
        double half = 0.5;
        double tradePlayerCountDecider = getRandomDouble();
        boolean tradeOnePlayerFlag = false;
        if(half < tradePlayerCountDecider || trading.getMaxPlayersPerTrade() == 1){
            tradeOnePlayerFlag = true;
        }
        return tradeOnePlayerFlag;
    }

    private boolean tradeAssumptionFlag(){
        double tradeAssumptionValue = 0.1;
        boolean flag = false;
        double randomValue = getRandomDouble();
        if(randomValue < tradeAssumptionValue){
            flag = true;
        }
        return flag;
    }

    private void findBestSwapPlayer(ITeam team, ILeague league,
                                   IPlayer weakestPlayer, Map<String, Object> swap) {
        boolean strongWeakPlayerTradeDecider = strongWeakPlayerTradeDecider();
        boolean tradePlayerCountDecider = tradePlayerCountDecider();
        IPlayer swapPlayer = null;
        List<IConference> conferenceList = league.getConferenceList();

        for (IConference conference : conferenceList) {
            if(tradeAssumptionFlag()){
                break;
            }
            List<IDivision> divisionList = conference.getDivisionList();
            for (IDivision division : divisionList) {
                List<ITeam> teamList = division.getTeamList();
                for (ITeam otherTeam : teamList) {
                    if (team.getName().equals(otherTeam.getName())) {
                        continue;
                    } else {
                        if (checkCurrentTradeOffer(otherTeam)) {
                            if (strongWeakPlayerTradeDecider) {
                                if (tradePlayerCountDecider) {
                                    swapPlayer = findOneToOneStrongPlayer(otherTeam,
                                            weakestPlayer, swapPlayer, swap);
                                } else {
                                    swapPlayer = findOneToManyStrongPlayer(otherTeam,
                                            swapPlayer, swap);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private IPlayer findOneToManyStrongPlayer(ITeam otherTeam,
                                              IPlayer swapPlayer, Map<String, Object> swap){
        List<IPlayer> fromPlayerListBeforeTrade = (List<IPlayer>) swap.get(FROMPLAYERLISTBEFORETRADE);
        List<IPlayer> playerList = otherTeam.getPlayerList();
        double weakPlayersStrengthTotal = ZERO;
        int ONE = 1;
        int TWO = 2;

        for(IPlayer weakPlayer : fromPlayerListBeforeTrade){
            weakPlayersStrengthTotal = weakPlayersStrengthTotal + weakPlayer.getRelativeStrength();
        }

        for (IPlayer player : playerList) {
            if ((swapPlayer == null) || (swapPlayer.getRelativeStrength() < player.getRelativeStrength())) {
                boolean conditionSatisfied = findStrongPlayerAlgorithm(weakPlayersStrengthTotal,
                        player.getRelativeStrength(), swap, player.getPosition().toString());
                if(conditionSatisfied) {
                    swapPlayer = player;
                    List<IPlayer> toPlayerList = new ArrayList<>(Arrays.asList(player));
                    swap.put(TOPLAYERLIST, toPlayerList);
                    swap.put(TOTEAM, otherTeam);
                    Object swappedDraftPick = swap.get(TRADED_ROUND_NUMBER);
                    if(swappedDraftPick == null) {
                        swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListBeforeTrade);
                    } else if((ONE == (int) swappedDraftPick || TWO == (int) swappedDraftPick) && strongWeakPlayerTradeDecider()){
                        swap.put(FROMPLAYERLISTAFTERTRADE, new ArrayList<>());
                    } else {
                        swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListBeforeTrade);
                    }
                }
                if(tradeAssumptionFlag()){
                    break;
                }
            }
        }
        return swapPlayer;
    }

    private IPlayer findOneToOneStrongPlayer(ITeam otherTeam, IPlayer weakestPlayer,
                                            IPlayer swapPlayer, Map<String, Object> swap) {
        List<IPlayer> playerList = otherTeam.getPlayerList();
        List<IPlayer> weakPlayerList = new ArrayList<>(Arrays.asList(weakestPlayer));

        for (IPlayer player : playerList) {
            if ((swapPlayer == null) || (swapPlayer.getRelativeStrength() < player.getRelativeStrength())) {
                boolean conditionSatisfied = findStrongPlayerAlgorithm(weakestPlayer.getRelativeStrength(),
                        player.getRelativeStrength(), swap, player.getPosition().toString());
                if(conditionSatisfied) {
                    swapPlayer = player;
                    List<IPlayer> toPlayerList = new ArrayList<>(Arrays.asList(player));
                    swap.put(TOPLAYERLIST, toPlayerList);
                    swap.put(TOTEAM, otherTeam);
                    swap.put(FROMPLAYERLISTAFTERTRADE, weakPlayerList);
                }
                if(tradeAssumptionFlag()){
                    break;
                }
            }
        }
        return swapPlayer;
    }

    private boolean findStrongPlayerAlgorithm(double weakStrength, double swapStrength,
                                              Map<String, Object> swap, String position){
        double thirtyFive = 0.35;
        double fortyFive = 0.45;
        double fiftyFive = 0.55;
        double sixty = 0.60;
        double seventy = 0.70;
        double seventyFive = 0.75;
        double eighty = 0.80;
        double ninety = 0.9;
        boolean conditionSatisfied = false;
        String FORWARD = "forward";
        String DEFENSE = "defense";
        int ONE = 1;
        int TWO = 2;
        int THREE = 3;
        int FOUR = 4;
        int FIVE = 5;
        int SIX = 6;

        if(weakStrength < (swapStrength * thirtyFive)){
            return conditionSatisfied;
        }
        else if(((weakStrength >= (swapStrength * thirtyFive))
                && (weakStrength < (swapStrength * fortyFive)))
                && (position.equalsIgnoreCase(DEFENSE))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag()) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[ZERO]);
            } else{
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[ONE]);
            }
        }
        else if(((weakStrength >= (swapStrength * fortyFive))
                && (weakStrength < (swapStrength * fiftyFive)))
                && (position.equalsIgnoreCase(FORWARD) || position.equalsIgnoreCase(DEFENSE))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag()) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[ONE]);
            } else{
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[TWO]);
            }
        }
        else if((weakStrength >= (swapStrength * fiftyFive))
                && (weakStrength < (swapStrength * sixty))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag()) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[TWO]);
            } else{
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[THREE]);
            }
        }
        else if((weakStrength >= (swapStrength * sixty))
                && (weakStrength < (swapStrength * seventy))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag()) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[THREE]);
            }
        }
        else if((weakStrength >= (swapStrength * seventy))
                && (weakStrength < (swapStrength * seventyFive))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag()) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[FOUR]);
            }
        }
        else if((weakStrength >= (swapStrength * seventyFive))
                && (weakStrength < (swapStrength * eighty))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag()) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[FIVE]);
            }
        }
        else if((weakStrength >= (swapStrength * eighty))
                && (weakStrength < (swapStrength * ninety))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag()) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[SIX]);
            }
        }
        else if((weakStrength >= (swapStrength * ninety))
                && (weakStrength < swapStrength)){

            conditionSatisfied = true;
        }
        return conditionSatisfied;
    }

    public void createTradeOffer(Map<String, Object> swap) {
        String PENDING = "pending";
        ITeam fromTeam = (ITeam) swap.get(FROMTEAM);
        ITeam toTeam = (ITeam) swap.get(TOTEAM);
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
        List<IPlayer> fromPlayerListBeforeTrade = (List<IPlayer>) swap.get(FROMPLAYERLISTAFTERTRADE);
        List<IPlayer> toPlayerList = (List<IPlayer>) swap.get(TOPLAYERLIST);
        ITradeOffer tradeOffer = (ITradeOffer) swap.get(TRADEOFFER);
        List<Integer> fromPlayerIdList = new ArrayList<>();
        List<Integer> toPlayerIdList = new ArrayList<>();

        try {
            for(IPlayer toPlayer : toPlayerList){
                toPlayerIdList.add(toPlayer.getId());
            }
            tradeOffer.setToPlayerIdList(toPlayerIdList);
            for (IPlayer player : fromPlayerListBeforeTrade) {
                fromPlayerIdList.add(player.getId());
            }
            tradeOffer.setFromPlayerIdList(fromPlayerIdList);
            tradeOffer.setToPlayerIdList(toPlayerIdList);
        } catch (NullPointerException e){
            log.error("ExecuteTradeState: createSwap: NullPointerException: "+e);
            throw e;
        } catch (Exception e){
            log.error("ExecuteTradeState: createSwap: Exception: "+e);
            throw e;
        }
    }

    public void performTrade(Map<String, Object> swap) {
        ITeam toTeam = (ITeam) swap.get(TOTEAM);
        ITeam fromTeam = (ITeam) swap.get(FROMTEAM);
        fromTeam.setLossPoint(ZERO);
        createTradeOffer(swap);
        if (toTeam.isAiTeam()) {
            if (acceptRejectTradeOffer(swap)) {
                updateTradingDetails(swap);
                consoleOutput.printMsgToConsole("Below trade is accepted successfully.");
            } else {
                ITradeOffer tradeOffer = (ITradeOffer) swap.get(TRADEOFFER);
                tradeOffer.setStatus(REJECTED);
                consoleOutput.printMsgToConsole("Below trade is rejected.");
            }
            consoleOutput.printTradeDetailsToUser(swap);
        } else {
            performUserTrade(swap);
        }
    }

    public void performUserTrade(Map<String, Object> swap) {
        consoleOutput.printMsgToConsole("Below Trade offer is received.");
        consoleOutput.printTradeDetailsToUser(swap);
        String userResponse = readUserInput.getUserTradeResponse();
        String a = "A";
        if (userResponse.equalsIgnoreCase(a.trim())) {
            updateTradingDetails(swap);
            consoleOutput.printMsgToConsole("Trade offer is accepted successfully.");
        } else {
            ITradeOffer tradeOffer = (ITradeOffer) swap.get(TRADEOFFER);
            tradeOffer.setStatus(REJECTED);

            consoleOutput.printMsgToConsole("Trade offer rejected successfully.");
        }
    }

    public boolean acceptRejectTradeOffer(Map<String, Object> tradeDetails) {
        boolean isTraded = false;
        ITeam fromTeam = (ITeam) tradeDetails.get(FROMTEAM);
        double characterValue = 0.0;
        double tradeAcceptChance = getRandomDouble();

        for(String key : trading.getGmTable().keySet()){
            if(key.equalsIgnoreCase(fromTeam.getManager().getPersonality())) {
                characterValue = trading.getGmTable().get(key);
                break;
            }
        }
        if (tradeAcceptChance < (trading.getRandomAcceptanceChance() + characterValue)) {
            isTraded = true;
        }
        return isTraded;
    }

    private void updateTradingDetailsInTeams(Map<String, Object> tradeDetails){
        ITeam fromTeam = (ITeam) tradeDetails.get(FROMTEAM);
        List<IPlayer> toPlayerList = (List<IPlayer>) tradeDetails.get(TOPLAYERLIST);
        List<IPlayer> fromPlayerList = (List<IPlayer>) tradeDetails.get(FROMPLAYERLISTAFTERTRADE);
        ITeam toTeam = (ITeam) tradeDetails.get(TOTEAM);
        List<IPlayer> toTeamPlayerList = toTeam.getPlayerList();
        List<IPlayer> fromTeamPlayerList = fromTeam.getPlayerList();

        try {
            for(IPlayer toPlayer : toPlayerList){
                fromTeamPlayerList.add(toPlayer);
                toPlayer.setTeamId(fromTeam.getId());
                removeObjectFromList(toTeamPlayerList, toPlayer);
                if (toPlayer.isCaptain()) {
                    toPlayer.setCaptain(false);
                    Collections.sort(toTeamPlayerList, Collections.reverseOrder());
                    toTeamPlayerList.get(ZERO).setCaptain(true);
                }
            }

            for (IPlayer player : fromPlayerList) {
                removeObjectFromList(fromTeamPlayerList, player);
                toTeamPlayerList.add(player);
                player.setTeamId(toTeam.getId());
                if (player.isCaptain()) {
                    player.setCaptain(false);
                    Collections.sort(fromTeamPlayerList, Collections.reverseOrder());
                    fromTeamPlayerList.get(ZERO).setCaptain(true);
                }
            }
        } catch (NullPointerException e){
            log.error("ExecuteTradeState: updateTradingDetailsInTeams: NullPointerException: "+e);
            e.printStackTrace();
            throw e;
        } catch (Exception e){
            log.error("ExecuteTradeState: updateTradingDetailsInTeams: Exception: "+e);
            throw e;
        }
    }

    public void updateTradingDetails(Map<String, Object> tradeDetails) {
        String ACCEPTED = "accepted";
        ITeam fromTeam = (ITeam) tradeDetails.get(FROMTEAM);
        ITeam toTeam = (ITeam) tradeDetails.get(TOTEAM);
        ITradeOffer tradeOffer = (ITradeOffer) tradeDetails.get(TRADEOFFER);

        updateTradingDetailsInTeams(tradeDetails);
        fromTeam.fixTeamAfterTrading(league.getFreeAgent().getPlayerList());
        toTeam.fixTeamAfterTrading(league.getFreeAgent().getPlayerList());
        tradeOffer.setStatus(ACCEPTED);
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

    public boolean checkCurrentTradeOffer(ITeam team) {
        boolean isTradingAllowed = false;
        if (team.getPlayersTradedCount() < trading.getMaxPlayersPerTrade()) {
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
