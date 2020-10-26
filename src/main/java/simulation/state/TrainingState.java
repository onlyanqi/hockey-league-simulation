package simulation.state;
import simulation.model.*;
import userIO.ConsoleOutput;

import java.util.List;
import java.util.Random;

public class TrainingState implements ISimulateState {
    @Override
    public ISimulateState action() {
        ConsoleOutput.printToConsole("Training Players and Team!");
        return exit();
    }

    private void StatIncreaseCheck(League league) {
        List<Conference> conferenceList=league.getConferenceList();
        for(Conference conference : conferenceList){
            List<Division> divisionList = conference.getDivisionList();
            for(Division division : divisionList){
                List<Team> teamList = division.getTeamList();
                for(Team team : teamList){
                    List<Player> playerList = team.getPlayerList();
                    for(Player player : playerList){
                        StatIncreaseCheckPlayer(player, team.getCoach());
                    }
                }
            }
        }
    }

    private void StatIncreaseCheckPlayer(Player player, Coach headCoach){

        double coachStrengthShooting=headCoach.getShooting();
        double coachStrengthSkating = headCoach.getSkating();
        double coachStrengthChecking = headCoach.getChecking();
        double coachStrengthSaving = headCoach.getSaving();

        if(isRandomLess(coachStrengthShooting)){
            if(isStrengthInRangeAfterIncrease(player.getShooting()+1)){
                player.setShooting(player.getShooting()+1);
            }
        }else{
            // run injury check
        }
        if(isRandomLess(coachStrengthSkating)){
            if(isStrengthInRangeAfterIncrease(player.getSkating()+1)){
                player.setSkating(player.getSkating()+1);
            }

        }else{
            // run injury check
        }
        if(isRandomLess(coachStrengthChecking)){
            if(isStrengthInRangeAfterIncrease(player.getChecking()+1)){
                player.setChecking(player.getChecking()+1);
            }

        }else{
            // run injury check
        }
        if(isRandomLess(coachStrengthSaving)){
            if(isStrengthInRangeAfterIncrease(player.getSaving()+1)){
                player.setSaving(player.getSaving()+1);
            }
        }else{
            // run injury check
        }
    }

    boolean isStrengthInRangeAfterIncrease(int strengthAfterIncrease){
        if(strengthAfterIncrease>=1 && strengthAfterIncrease<=20){
            return true;
        }else{
            return false;
        }
    }

    boolean isRandomLess(double coachStrength){
        Random rand = new Random();
        double randomNumber = rand.nextDouble();
        if(randomNumber < coachStrength){
            return true;
        }else{
            return false;
        }
    }

    private ISimulateState exit() {
        Boolean unplannedGamesScheduled = true;
        Boolean tradeDeadlinePassed = true;

        if(unplannedGamesScheduled){
            return new SimulateGameState();
        }else{
            if(tradeDeadlinePassed){
                return new AgingState();
            }else{
                return new ExecuteTradeState();
            }
        }

    }
}
