package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import presentation.ReadUserInput;
import simulation.model.*;

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
    private final String TYPE = "type";
    private final String STRONG = "strong";
    private final String WEAK = "weak";
    private final Integer[] tradedDraftRound = {1, 2, 3, 4, 5, 6, 7};
    private final int ZERO = 0;
    private final int ONE = 1;
    private final String TRADED_ROUND_NUMBER = "tradedRoundNumber";
    private final String TOPLAYERLIST = "toPlayerList";
    private ITrading trading = null;
    private IHockeyContext hockeyContext;
    private ILeague league;
    private ConsoleOutput consoleOutput;
    private ReadUserInput readUserInput;
    private static final Logger log = Logger.getLogger(ExecuteTradeState.class);

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
        Map<String, Object> swap;
        try {
            if (checkTradingPeriod(trading, league.getCurrentDate())) {
                if (checkCurrentTradeOffer(team)) {
                    double tradeOfferChance = getRandomDouble();
                    if (tradeOfferChance < trading.getRandomTradeOfferChance()) {
                        List<IPlayer> tradingPlayerList = getWeakestPlayerList(team, league);
                        Collections.sort(tradingPlayerList);
                        for (IPlayer weakestPlayer : tradingPlayerList) {
                            if (checkLossPoint(team, trading)) {
                                swap = new HashMap<>();
                                swap.put(FROMPLAYERLISTBEFORETRADE, tradingPlayerList);
                                swap.put(FROMTEAM, team);
                                findBestSwapPlayer(team, league, weakestPlayer, swap);
                                draftPickTradeDecider(swap);
                                ITeam toTeam = (ITeam) swap.get(TOTEAM);
                                if(toTeam == null){
                                    continue;
                                }
                                performTrade(swap);
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

    private void draftPickTradeDecider(Map<String, Object> tradeDetails){
        Object type = tradeDetails.get(TYPE);
        Object swappedDraftPick = tradeDetails.get(TRADED_ROUND_NUMBER);
        int TWO = 2;

        if(type == null || swappedDraftPick == null){
            return;
        } else if(fiftyFiftyDecider() && STRONG.equalsIgnoreCase((String) type)
            && (ONE == (int) swappedDraftPick || TWO == (int) swappedDraftPick)){

            tradeDetails.put(FROMPLAYERLISTAFTERTRADE, new ArrayList<>());
        } else if(fiftyFiftyDecider() && WEAK.equalsIgnoreCase((String) type)
            && (ONE == (int) swappedDraftPick || TWO == (int) swappedDraftPick)){

            tradeDetails.put(TOPLAYERLIST, new ArrayList<>());
        } else if(tradeAssumptionFlag()){
            tradeDetails.put(TRADED_ROUND_NUMBER, 0);
        }
    }

    private boolean fiftyFiftyDecider(){
        double half = 0.5;
        double strongWeakPlayerDecider = getRandomDouble();
        boolean getStrongPlayerFlag = false;

        if(half < strongWeakPlayerDecider){
            getStrongPlayerFlag = true;
        }
        return getStrongPlayerFlag;
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
        IPlayer swapPlayer = null;
        List<IConference> conferenceList = league.getConferenceList();
        double tradeDecider = getRandomDouble();
        double strongOneToOneTradeDecider = 0.2;
        double strongOneToManyTradeDecider = 0.4;
        double weakOneToOneTradeDecider = 0.6;
        double weakOneToManyTradeDecider = 0.8;

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
                            if (tradeDecider < strongOneToOneTradeDecider) {
                                swapPlayer = findOneToOneStrongPlayer(otherTeam,
                                            weakestPlayer, swapPlayer, swap);
                            } else if(tradeDecider < strongOneToManyTradeDecider) {
                                swapPlayer = findOneToManyStrongPlayer(otherTeam,
                                            swapPlayer, swap);
                            } else if(tradeDecider < weakOneToOneTradeDecider){
                                swapPlayer = findOneToOneWeakPlayer(otherTeam,
                                            weakestPlayer, swapPlayer, swap);
                            } else if(tradeDecider < weakOneToManyTradeDecider) {
                                findOneToManyWeakPlayer(otherTeam, swap);
                            } else{
                                manyToManyTrade(otherTeam, swap);
                            }
                        }
                    }
                }
            }
        }
    }

    private void manyToManyTrade(ITeam otherTeam, Map<String, Object> swap){
        List<IPlayer> fromPlayerListBeforeTrade = (List<IPlayer>) swap.get(FROMPLAYERLISTBEFORETRADE);
        boolean draftPick = fiftyFiftyDecider();
        List<IPlayer> toPlayerList = new ArrayList<>();

        for(IPlayer weakPlayer : fromPlayerListBeforeTrade) {
            IPlayer swapPlayer = null;
            if (draftPick) {
                swapPlayer = findOneToOneWeakPlayer(otherTeam, weakPlayer, swapPlayer, swap);
                if(swapPlayer == null){
                    continue;
                } else{
                    boolean alreadyTaken = false;
                    for(IPlayer player : toPlayerList){
                        if(player.getId() == swapPlayer.getId()){
                            alreadyTaken = true;
                            swapPlayer = null;
                        }
                    }
                    if(alreadyTaken){
                        continue;
                    } else{
                        toPlayerList.add(swapPlayer);
                    }
                }
            } else{
                swapPlayer = findOneToOneStrongPlayer(otherTeam, weakPlayer, swapPlayer, swap);
                if(swapPlayer == null){
                    continue;
                } else{
                    boolean alreadyTaken = false;
                    for(IPlayer player : toPlayerList){
                        if(player.getId() == swapPlayer.getId()){
                            alreadyTaken = true;
                            swapPlayer = null;
                        }
                    }
                    if(alreadyTaken){
                        continue;
                    } else{
                        toPlayerList.add(swapPlayer);
                    }
                }
            }
        }
        if(toPlayerList == null || toPlayerList.isEmpty()){
            return;
        } else{
            swap.put(TOPLAYERLIST, toPlayerList);
            swap.put(FROMPLAYERLISTAFTERTRADE, swap.get(FROMPLAYERLISTBEFORETRADE));
        }
    }

    private void findOneToManyWeakPlayer(ITeam otherTeam, Map<String, Object> swap){
        List<IPlayer> fromPlayerListBeforeTrade = (List<IPlayer>) swap.get(FROMPLAYERLISTBEFORETRADE);
        List<IPlayer> toPlayerList = new ArrayList<>();
        double newStrength = 0;

        for(IPlayer weakPlayer : fromPlayerListBeforeTrade) {
            IPlayer swapPlayer = null;
            swapPlayer = findOneToOneWeakPlayer(otherTeam, weakPlayer, swapPlayer, swap);
            if (swapPlayer == null) {
                continue;
            } else {
                boolean alreadyTaken = false;
                for (IPlayer player : toPlayerList) {
                    if (player.getId() == swapPlayer.getId()) {
                        alreadyTaken = true;
                        swapPlayer = null;
                    }
                }
                if (alreadyTaken) {
                    continue;
                } else {
                    newStrength = newStrength + swapPlayer.getRelativeStrength();
                    toPlayerList.add(swapPlayer);
                }
            }
        }

        if(toPlayerList == null || toPlayerList.isEmpty()){
            return;
        } else{
            swap.put(TOPLAYERLIST, toPlayerList);
        }
    }

    private IPlayer findOneToManyStrongPlayer(ITeam otherTeam,
                                              IPlayer swapPlayer, Map<String, Object> swap){
        List<IPlayer> fromPlayerListBeforeTrade = (List<IPlayer>) swap.get(FROMPLAYERLISTBEFORETRADE);
        List<IPlayer> playerList = otherTeam.getPlayerList();
        double weakPlayersStrengthTotal = ZERO;
        ITeam fromTeam = (ITeam) swap.get(FROMTEAM);
        List<String> draftPicks = fromTeam.getDraftPicks();
        int weakAge = 0;
        for(IPlayer weakPlayer : fromPlayerListBeforeTrade){
            weakPlayersStrengthTotal = weakPlayersStrengthTotal + weakPlayer.getRelativeStrength();
            weakAge = weakAge + weakPlayer.getAge();
        }

        for (IPlayer player : playerList) {
            boolean ageFactor = getAgeFactor(weakAge / fromPlayerListBeforeTrade.size(), player.getAge() );
            List<String> positions = new ArrayList<>();
            positions.add(player.getPosition().toString());
            if ((swapPlayer == null) || (swapPlayer.getRelativeStrength() < player.getRelativeStrength())
                && (ageFactor)) {
                boolean conditionSatisfied = findPlayerAlgorithm(weakPlayersStrengthTotal,
                        player.getRelativeStrength(), swap, positions,
                        draftPicks);
                if(conditionSatisfied) {
                    swapPlayer = player;
                    List<IPlayer> toPlayerList = new ArrayList<>();
                    toPlayerList.add(player);
                    swap.put(TOPLAYERLIST, toPlayerList);
                    swap.put(TOTEAM, otherTeam);
                    swap.put(TYPE, STRONG);
                    swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerListBeforeTrade);
                }
                if(tradeAssumptionFlag()){
                    break;
                }
            }
        }
        return swapPlayer;
    }

    private boolean getAgeFactor(int weakAge, int swapAge){
        int twentyThree = 23;
        int thirtyThree = 33;
        boolean ageFactor = false;
        if(weakAge <= twentyThree){
            if(swapAge >= thirtyThree){
                ageFactor = true;
            }
        }else if(weakAge < thirtyThree){
            if(swapAge < thirtyThree){
                ageFactor = true;
            }
        }
        return ageFactor;
    }

    private IPlayer findOneToOneStrongPlayer(ITeam otherTeam, IPlayer weakestPlayer,
                                            IPlayer swapPlayer, Map<String, Object> swap) {
        List<IPlayer> playerList = otherTeam.getPlayerList();
        List<IPlayer> weakPlayerList = new ArrayList<>();
        weakPlayerList.add(weakestPlayer);
        ITeam fromTeam = (ITeam) swap.get(FROMTEAM);
        List<String> draftPicks = fromTeam.getDraftPicks();

        for (IPlayer player : playerList) {
            boolean ageFactorOnetoOne = getAgeFactor(weakestPlayer.getAge(), player.getAge());
            List<String> positions = new ArrayList<>();
            positions.add(player.getPosition().toString());
            if ((swapPlayer == null) || (swapPlayer.getRelativeStrength() < player.getRelativeStrength())
                && (ageFactorOnetoOne)) {
                boolean conditionSatisfied = findPlayerAlgorithm(weakestPlayer.getRelativeStrength(),
                        player.getRelativeStrength(), swap, positions, draftPicks);
                if(conditionSatisfied) {
                    swapPlayer = player;
                    List<IPlayer> toPlayerList = new ArrayList<>();
                    toPlayerList.add(player);
                    swap.put(TOPLAYERLIST, toPlayerList);
                    swap.put(TOTEAM, otherTeam);
                    swap.put(FROMPLAYERLISTAFTERTRADE, weakPlayerList);
                    swap.put(TYPE, STRONG);
                }
                if(tradeAssumptionFlag()){
                    break;
                }
            }
        }
        return swapPlayer;
    }

    private IPlayer findOneToOneWeakPlayer(ITeam otherTeam, IPlayer fromPlayer,
                                             IPlayer swapPlayer, Map<String, Object> swap) {
        List<IPlayer> playerList = otherTeam.getPlayerList();
        List<IPlayer> fromPlayerList = new ArrayList<>(Arrays.asList(fromPlayer));
        List<String> draftPicks = otherTeam.getDraftPicks();
        List<String> positions = new ArrayList<>();
        positions.add(fromPlayer.getPosition().toString());

        for (IPlayer player : playerList) {
            boolean ageFactorOnetoOne = getAgeFactor(player.getAge(), fromPlayer.getAge());
            if ((swapPlayer == null) || (player.getRelativeStrength() < swapPlayer.getRelativeStrength())
                && (ageFactorOnetoOne)) {
                boolean conditionSatisfied = findPlayerAlgorithm(player.getRelativeStrength(),
                        fromPlayer.getRelativeStrength(), swap, positions, draftPicks);
                if(conditionSatisfied) {
                    swapPlayer = player;
                    List<IPlayer> toPlayerList = new ArrayList<>();
                    toPlayerList.add(player);
                    swap.put(TOPLAYERLIST, toPlayerList);
                    swap.put(TOTEAM, otherTeam);
                    swap.put(FROMPLAYERLISTAFTERTRADE, fromPlayerList);
                    swap.put(TYPE, WEAK);
                }
            }
        }
        return swapPlayer;
    }

    private boolean findPlayerAlgorithm(double weakStrength, double swapStrength,
                                        Map<String, Object> swap, List<String> positions,
                                        List<String> draftPicks){
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
                && (positions.contains(DEFENSE))) {

            if(tradeAssumptionFlag() && draftPicks.get(ZERO) == null) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[ZERO]);
            } else if(draftPicks.get(ONE) == null){
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[ONE]);
            } else{
                return conditionSatisfied;
            }
            conditionSatisfied = true;
        }
        else if(((weakStrength >= (swapStrength * fortyFive))
                && (weakStrength < (swapStrength * fiftyFive)))
                && (positions.contains(FORWARD) || positions.contains(DEFENSE))) {

            if(tradeAssumptionFlag() && draftPicks.get(ONE) == null) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[ONE]);
            } else if(draftPicks.get(TWO) == null){
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[TWO]);
            } else {
                return conditionSatisfied;
            }
            conditionSatisfied = true;
        }
        else if((weakStrength >= (swapStrength * fiftyFive))
                && (weakStrength < (swapStrength * sixty))) {
            
            if(tradeAssumptionFlag() && draftPicks.get(TWO) == null) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[TWO]);
            } else if(draftPicks.get(THREE) == null){
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[THREE]);
            } else{
                return conditionSatisfied;
            }
            conditionSatisfied = true;
        }
        else if((weakStrength >= (swapStrength * sixty))
                && (weakStrength < (swapStrength * seventy))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag() && draftPicks.get(THREE) == null) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[THREE]);
            }
        }
        else if((weakStrength >= (swapStrength * seventy))
                && (weakStrength < (swapStrength * seventyFive))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag() && draftPicks.get(FOUR) == null) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[FOUR]);
            }
        }
        else if((weakStrength >= (swapStrength * seventyFive))
                && (weakStrength < (swapStrength * eighty))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag() && draftPicks.get(FIVE) == null) {
                swap.put(TRADED_ROUND_NUMBER, tradedDraftRound[FIVE]);
            }
        }
        else if((weakStrength >= (swapStrength * eighty))
                && (weakStrength < (swapStrength * ninety))) {

            conditionSatisfied = true;
            if(tradeAssumptionFlag() && draftPicks.get(SIX) == null) {
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
        IModelFactory tradeOfferConcrete = hockeyContext.getModelFactory();
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
        Object tradedDraftPick = tradeDetails.get(TRADED_ROUND_NUMBER);
        int tradeDraftRoundNumber;
        if(tradedDraftPick == null){
            tradeDraftRoundNumber = 0;
        } else{
            tradeDraftRoundNumber = (int) tradedDraftPick;
            List<String> draftPicks = fromTeam.getDraftPicks();
            draftPicks.add(tradeDraftRoundNumber, toTeam.getName());
        }

        List<IPlayer> toTeamPlayerList = toTeam.getPlayerList();
        List<IPlayer> fromTeamPlayerList = fromTeam.getPlayerList();
        fromTeam.setPlayersTradedCount(fromPlayerList.size());
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
