package presentation;

import simulation.model.ICoach;
import simulation.model.IManager;
import simulation.model.IPlayer;

import java.util.List;

public interface IConsoleOutputForTeamCreation {

    void showLeagueAlreadyExistsError();

    void showManagerListOnScreen(List<IManager> managerList);

    void showSuccessfulSerializationMessage();

    void showSuccessfulManagerCreationMessage();

    void showSuccessfulCoachCreationMessage();

    void showCoachListOnScreen(List<ICoach> coachList);

    void printCoach(int i, ICoach currentCoach);

    void showInstructionsForTeamCreation();

    void showGoodFreeAgentList(List<IPlayer> freeAgentList, List<Integer> goodFreeAgentsIdList);

    void playerIdAlreadyChosenMessage(List<Integer> chosenPlayersIdList);

    void showCountOfNeededPlayers(int numberOfGoalies, int numberOfForward, int numberOfDefense);

    void showTeamCreationWaitMessage();

    void showBelowAverageFreeAgentList(List<IPlayer> freeAgentList, List<Integer> goodFreeAgentsIdList);

    void printPlayer(int i, IPlayer player);

    void showNotEnoughMembersError();
}
