package simulation.state;

import simulation.model.*;

import java.util.List;

public class TradeState implements ISimulateState{

    @Override
    public ISimulateState action() {
        System.out.println("Trading Players!");
        return new BuyPlayerState();
    }

    public boolean isListNotEmpty(List list){
        boolean isNotEmpty = true;

        if(list == null || list.isEmpty()){
            isNotEmpty = false;
        }

        return isNotEmpty;
    }

    public void loopAllTeams(League league){
        if(league != null){
            List<Conference> conferenceList = league.getConferenceList();
            if(conferenceList != null && isListNotEmpty(conferenceList)){
                for(Conference conference : conferenceList){
                    List<Division> divisionList = conference.getDivisionList();
                    if(divisionList != null && isListNotEmpty(divisionList)){
                        for(Division division : divisionList){
                            List<Team> teamList = division.getTeamList();
                            if(teamList != null && isListNotEmpty(teamList)){
                                for(Team team : teamList){
                                    tradingLogic(team, league);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void tradingLogic(Team team, League league){
        if(checkTradingPeriod(league.getTrading())){
            if(checkLossPoint(team, league.getTrading())){

            }
        }
    }

    public boolean checkTradingPeriod(Trading trading){
        trading.isLeagueInTradingPeriod(league.getDate());
        return trading.isTradingPeriod();;
    }

    public boolean checkLossPoint(Team team, Trading trading){
        boolean isTradingNeeded = false;

        if(team.getLoss() >= trading.getLossPoint()){
            isTradingNeeded = true;
        }

        return isTradingNeeded;
    }


}
