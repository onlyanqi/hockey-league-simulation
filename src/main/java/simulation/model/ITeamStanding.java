package simulation.model;

import java.util.ArrayList;
import java.util.List;

public interface ITeamStanding {

    int getId();

    void setId(int id);

    List<ITeamScore> getTeamsScoreList();

    void setTeamsScoreList(List<ITeamScore> teamsScoreList);

    void initializeTeamStandings(List<String> teamNames);

    void initializeTeamStandingsRegularSeason(ILeague league);

    void setTeamPoints(String teamName);

    void setTeamWins(String teamName);

    void setTeamLoss(String teamName);

    void setTeamTies(String teamName);

    List<ITeamScore> getTeamsRankAcrossConference(ILeague league, String conferenceName);

    List<ITeamScore> getTeamsRankAcrossDivision(ILeague league, String divisionName);

    List<ITeamScore> sortTeamsScoreList(List<ITeamScore> teamsScoreList);

    ITeamScore getTeamScoreByTeamName(List<ITeamScore> teamsScoreList, String teamName);

}
