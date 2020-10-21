package simulation.state;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import simulation.factory.*;
import simulation.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrainingState implements ISimulateState {
    @Override
    public ISimulateState action() {
        System.out.println("Training Players and Team!");
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
                        StatIncreaseCheckPlayer(player, team.getHeadCoach());
                    }
                }
            }
        }
    }

    /*
    *Change headCoach to obj
    *
    * */


    private void StatIncreaseCheckPlayer(Player player, String headCoach){
        /*
         * For each skill run below code
         *
         * */

        double coachStrength=0.6;   // this will change according to coach's strength of specific  skill
        Random rand = new Random();
        double randomNumber = rand.nextDouble();
        if(randomNumber<coachStrength){
            //increase that specific stat by one
        }else{
            //trigger an injury check here
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
