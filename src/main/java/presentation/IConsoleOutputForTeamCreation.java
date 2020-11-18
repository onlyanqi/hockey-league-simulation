package presentation;

import simulation.model.Coach;
import simulation.model.Manager;
import simulation.model.Player;

import java.util.List;

public interface IConsoleOutputForTeamCreation {

    void showLeagueAlreadyExistsError();

    void showManagerListOnScreen(List<Manager> managerList);

    void showSuccessfulSerializationMessage();

    void showSuccessfulManagerCreationMessage();

    void showSuccessfulCoachCreationMessage();

    void showCoachListOnScreen(List<Coach> coachList);

    void printCoach(int i, Coach currentCoach);

    void showInstructionsForTeamCreation();

    void showGoodFreeAgentList(List<Player> freeAgentList, List<Integer> goodFreeAgentsIdList);

    void playerIdAlreadyChosenMessage(List<Integer> chosenPlayersIdList);

    void showCountOfNeededPlayers(int numberOfGoalies, int numberOfForward, int numberOfDefense);

    void showTeamCreationWaitMessage();

    void showBelowAverageFreeAgentList(List<Player> freeAgentList, List<Integer> goodFreeAgentsIdList);

    void printPlayer(int i, Player player);

    void showNotEnoughMembersError();
}
