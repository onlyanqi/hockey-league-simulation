package simulation.model;

import java.util.HashMap;

public class RegularSeasonScoreBoard {
    HashMap<String, Integer> teamsScore;

    public RegularSeasonScoreBoard() {
        this.teamsScore = new HashMap<>();
    }

    public void initializeScore(League league) {

        for (Conference conference2 : league.getConferenceList()) {
            for (Division division : conference2.getDivisionList()) {
                for (Team team : division.getTeamList()) {
                    teamsScore.put(team.getName(), 0);
                }
            }
        }
    }

    public HashMap<String, Integer> getTeamsScore() {
        return teamsScore;
    }

    public void setTeamsScore(HashMap<String, Integer> teamScore) {
        this.teamsScore = teamScore;
    }

    public void setTeamScore(String teamName) {
        int previousTeamScore = teamsScore.get(teamName);
        int newTeamScore = previousTeamScore + 2;
        teamsScore.put(teamName, newTeamScore);
    }

}
