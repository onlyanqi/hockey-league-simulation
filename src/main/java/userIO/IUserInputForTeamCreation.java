package userIO;

import simulation.model.Coach;
import simulation.model.Manager;

import java.util.List;

public interface IUserInputForTeamCreation {
    public String getConferenceName(List<String> conferenceNameList);

    String getDivisionName(List<String> divisionNameList);

    String getUserChoiceForSerialization();

    String getTeamName(List<String> teamNameList);

    int getPlayerId(int upperBound);

    int getGeneralManagerId(List<Manager> managerList);

    int getHeadCoachId(List<Coach> coachList);
}
