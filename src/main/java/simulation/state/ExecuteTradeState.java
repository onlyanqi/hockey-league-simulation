package simulation.state;

import simulation.model.*;

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
        if(checkTradingPeriod(league.getTrading(), league.getCurrentDate())){
            if(checkLossPoint(team, league.getTrading())){
                if(checkCurrentTradeOffer(team, league)) {
                    double tradeOfferChance = getRandomDouble();
                    if (tradeOfferChance < league.getTrading().getRandomTradeOfferChance()) {
                        List<Player> tradingPlayerList = getWeakestPlayerList(team, league);
                        if(isListNotEmpty(tradingPlayerList)) {
                            for(Player weakestPlayer : tradingPlayerList) {
                                findBestSwapPlayer(team, league, weakestPlayer, swap);
                                Player swapPlayer = (Player) swap.get(TOPLAYER);
                                if (isNotNull(swapPlayer)) {
                                    TradeOffer tradeOffer = new TradeOffer();
                                    tradeOffer.setLeagueId(league.getId());
                                    tradeOffer.setTradingId(league.getTrading().getId());
                                    tradeOffer.setStatus(PENDING);
                                    tradeOffer.setFromPlayerId(weakestPlayer.getId());
                                    tradeOffer.setFromTeamId(weakestPlayer.getTeamId());
                                    tradeOffer.setToTeamId(swapPlayer.getTeamId());
                                    tradeOffer.setToPlayerId(swapPlayer.getId());
                                    league.getTradingOfferList().add(tradeOffer);
                                    swap.put(TRADEOFFER, tradeOffer);
                                    swap.put(TRADING, league.getTrading());
                                    swap.put(FROMTEAM, team);
                                    if (acceptRejectTradeOffer(swap)) {
                                        updateTradingDetails(swap);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean acceptRejectTradeOffer(Map<String, Object> tradeDetails){
        boolean isTraded = false;
        if(isNotNull(tradeDetails)) {
            Trading trading = (Trading) tradeDetails.get(TRADING);
            if(isNotNull(trading)) {
                Team toTeam = (Team) tradeDetails.get(TOTEAM);
                Player fromPlayer = (Player) tradeDetails.get(FROMPLAYER);
                Player toPlayer = (Player) tradeDetails.get(TOPLAYER);
                if (checkTeamStrength(toTeam, toPlayer, fromPlayer)) {
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
        Team swapTeam = null;
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
                                    if(team.getId() == otherTeam.getId()) {
                                        continue;
                                    }
                                    else{
                                        if (checkCurrentTradeOffer(otherTeam, league)) {
                                            swapPlayer = algorithmToFindSwapPlayer(otherTeam,
                                                    league, weakestPlayer, swapPlayer);
                                            swapTeam = otherTeam;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        swap.put(FROMPLAYER, weakestPlayer);
        swap.put(TOPLAYER, swapPlayer);
        swap.put(TOTEAM, swapTeam);
    }

    public Player algorithmToFindSwapPlayer(Team otherTeam,
                                            League league, Player weakestPlayer, Player swapPlayer){

        List<Player> playerList = otherTeam.getPlayerList();
        if(isListNotEmpty(playerList)){
            for(Player player:playerList) {

                List<TradeOffer> tradeOfferList = league.getTradingOfferList();
                if(checkExistingTradeOffer(weakestPlayer.getId(), player.getId(), tradeOfferList)) {

                    //if((player.getPlayerTradeStatus() == null ||
                      //      player.getPlayerTradeStatus().equals(PENDING) ||
                        //    player.getPlayerTradeStatus().equals(BOUGHT))) {

                    if (isNotNull(weakestPlayer.getPosition()) && isNotNull(player.getPosition()) &&
                            (weakestPlayer.getPosition().equals(player.getPosition()))
                            && ((swapPlayer == null) || (swapPlayer.getStrength() < player.getStrength() ))) {

                        swapPlayer = player;
                    }
                    //}
                }
            }
        }
        return swapPlayer;
    }

    public boolean checkExistingTradeOffer(int fromPlayerId, int toPlayerId,
                                           List<TradeOffer> tradeOfferList){
        boolean isNewTrade = true;
        if(isListNotEmpty(tradeOfferList)){
            for(TradeOffer tradeOffer : tradeOfferList){
                if((isNotNull(tradeOffer.getStatus()) && tradeOffer.getStatus().equals(PENDING))
                        && (tradeOffer.getFromPlayerId() == fromPlayerId
                        || tradeOffer.getToPlayerId() == toPlayerId)){

                    isNewTrade = false;
                    break;
                }
            }
        }
        return isNewTrade;
    }

    public List<Player> getWeakestPlayerList(Team team, League league){
        List<Player> playerList = team.getPlayerList();
        List<Player> returnPlayerList = new ArrayList<>();
        if(isListNotEmpty(playerList)){
            for(Player player : playerList){
                boolean isPlayerEligible = true;
                if(returnPlayerList.size() < league.getTrading().getMaxPlayersPerTrade()) {
                    List<TradeOffer> tradeOfferList = league.getTradingOfferList();
                    if (isListNotEmpty(tradeOfferList)) {
                        for (TradeOffer tradeOffer : tradeOfferList) {
                            if (tradeOffer.getFromPlayerId() == player.getId()
                                    || tradeOffer.getToPlayerId() == player.getId()){
                                isPlayerEligible = false;
                                break;
                            } else if(isNotNull(tradeOffer.getStatus()) && tradeOffer.getStatus().equals(PENDING)) {
                                continue;
                            }
                        }
                        if(isPlayerEligible) {
                            returnPlayerList.add(player);
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return returnPlayerList;
    }

    /*
    public void updatePlayerTradeStatus(Team team, League league){
        if(team != null){
            List<Player> playerList = team.getPlayerList();
            if(isListNotEmpty(playerList)){
                Collections.sort(playerList);
                List<TradeOffer> tradeOfferList = league.getTradingOfferList();
                if(isListNotEmpty(tradeOfferList)){
                    for(Player player : playerList) {
                        //if(player.getPlayerTradeStatus() != null) {
                        for (TradeOffer tradeOffer : tradeOfferList) {
                            String OFFERED = "offered";
                            String SOLD = "sold";

                            if (team.getId() == tradeOffer.getFromTeamId()) {
                                if (player.getId() == tradeOffer.getToPlayerId()
                                        && tradeOffer.getStatus().equals(ACCEPTED)) {

                                    player.setPlayerTradeStatus(BOUGHT);
                                } else if ((player.getId() == tradeOffer.getFromPlayerId() ||
                                        player.getId() == tradeOffer.getToPlayerId())
                                        && tradeOffer.getStatus().equals(PENDING)) {

                                    player.setPlayerTradeStatus(OFFERED);
                                } else if (player.getId() == tradeOffer.getFromPlayerId()
                                        && tradeOffer.getStatus().equals(ACCEPTED)) {

                                    player.setPlayerTradeStatus(SOLD);
                                }
                            } else if (team.getId() == tradeOffer.getToTeamId()){
                                if(player.getId() == tradeOffer.getFromPlayerId()
                                        && tradeOffer.getStatus().equals(ACCEPTED)){

                                    player.setPlayerTradeStatus(BOUGHT);
                                } else if((player.getId() == tradeOffer.getFromPlayerId()
                                        || player.getId() == tradeOffer.getToPlayerId())
                                        && tradeOffer.getStatus().equals(PENDING)){

                                    player.setPlayerTradeStatus(OFFERED);
                                } else if(player.getId() == tradeOffer.getToPlayerId()
                                        && tradeOffer.getStatus().equals(ACCEPTED)){

                                    player.setPlayerTradeStatus(SOLD);
                                }
                            }
                        }
                        //}
                    }
                }
            }
        }
    }
    */

    public boolean checkCurrentTradeOffer(Team team, League league){
        boolean isTradingAllowed = false;
        List<TradeOffer> tradeOfferList = league.getTradingOfferList();
        if(isListNotEmpty(tradeOfferList)){
            for(TradeOffer tradeOffer : tradeOfferList){
                if((isNotNull(tradeOffer.getStatus()) && tradeOffer.getStatus().equals(PENDING))
                        && team.getId() == tradeOffer.getFromTeamId()){

                    team.setPendingTradeOfferCount(team.getPendingTradeOfferCount()+1);
                }
            }
            Trading trading = league.getTrading();
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
            if (player.getId() == toRemove.getId()) {
                itr.remove();
            }
        }

    }

    public ISimulateState exit() {
        return new AgingState(hockeyContext);
    }
}
