package simulation.state;

import simulation.model.Conference;
import simulation.model.Division;
import simulation.model.Player;

import java.util.List;

public interface ICreateTeamState {


    void getTeamName(Division division);

    Division chooseDivision(Conference conference);

    Conference chooseConference();

    void choosePlayers();

    void chooseCoach();

    void chooseManager();

    List<Player> createPlayerListByChosenPlayerId(List<Integer> chosenPlayersIdList, List<Player> freeAgentList);

    List<Player> removeChosenPlayersFromFreeAgentList(List<Integer> chosenPlayersIdList, List<Player> freeAgentList);

    boolean isLeaguePresent(String leagueName);
}
