package presentation;

import simulation.model.ICoach;
import simulation.model.IManager;

import java.util.List;

public interface IUserInputForTeamCreation {
    String getConferenceName(List<String> conferenceNameList);

    String getDivisionName(List<String> divisionNameList);

    String getUserChoiceForSerialization();

    String getTeamName(List<String> teamNameList);

    int getPlayerId(int upperBound);

    int getGeneralManagerId(List<IManager> managerList);

    int getHeadCoachId(List<ICoach> coachList);
}
