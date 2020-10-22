package simulation.model;

import java.util.HashMap;

public class ScoreBoard {
    HashMap<String, Integer> teamScore;

    public ScoreBoard() {
        this.teamScore = new HashMap<>();
    }

    public void initializeScore(League league){

        for(Conference conference2: league.getConferenceList())
            for(Division division: conference2.getDivisionList())
                for(Team team : division.getTeamList()){
                    teamScore.put(team.getName(),0);
                }

    }

    public HashMap<String, Integer> getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(HashMap<String, Integer> teamScore) {
        this.teamScore = teamScore;
    }
}
