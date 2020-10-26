package simulation.model;

import java.util.*;

public class TeamStanding {

    HashMap<String, Integer> teamsRank;
    RegularSeasonScoreBoard regularSeasonScoreBoard;

    public TeamStanding(HashMap<String, Integer> teamsRank, RegularSeasonScoreBoard regularSeasonScoreBoard) {
        this.teamsRank = teamsRank;
        this.regularSeasonScoreBoard = regularSeasonScoreBoard;
    }

    public TeamStanding(RegularSeasonScoreBoard regularSeasonScoreBoard) {
        this.regularSeasonScoreBoard = regularSeasonScoreBoard;
    }

    public HashMap<String, Integer> getTeamsRankAcrossLeague(){
        HashMap<String, Integer> teamsScore = regularSeasonScoreBoard.getTeamsScore();
        return sortHashMap(teamsScore);
    }

    public HashMap<String, Integer> getTeamsRankAcrossConference(League league, String conferenceName){

        HashMap<String, Integer> teamsScore = regularSeasonScoreBoard.getTeamsScore();
        HashMap<String,Integer> teamsScoreWithinConference = new HashMap<>();

        for(Conference conference: league.getConferenceList() ){
            for(Division division: conference.getDivisionList()){
                for(Team team: division.getTeamList()){
                    if(conference.getName().equals(conferenceName)){
                        teamsScoreWithinConference.put(team.getName(),teamsScore.get(team.getName()));
                    }
                }
            }
        }
        return sortHashMap(teamsScoreWithinConference);
    }

    public HashMap<String, Integer> getTeamsRankAcrossDivision(League league, String divisionName){
        HashMap<String, Integer> teamsScore = regularSeasonScoreBoard.getTeamsScore();
        HashMap<String,Integer> teamsScoreWithinDivision = new HashMap<>();

        for(Conference conference: league.getConferenceList() ){
            for(Division division: conference.getDivisionList()){
                for(Team team: division.getTeamList()){
                    if(division.getName().equals(divisionName)){
                        teamsScoreWithinDivision.put(team.getName(),teamsScore.get(team.getName()));
                    }
                }
            }
        }
        return sortHashMap(teamsScoreWithinDivision);
    }

    public HashMap<String, Integer> getTeamsRank() {
        return teamsRank;
    }

    public void setTeamsRank(HashMap<String, Integer> teamsRank) {
        this.teamsRank = teamsRank;
    }

    public HashMap<String, Integer> sortHashMap(HashMap<String, Integer> teamsScore){
        //Reference : https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > teamsScoreList =
                new LinkedList<Map.Entry<String, Integer> >(teamsScore.entrySet());

        // Sort the list
        Collections.sort(teamsScoreList, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> team1score,
                               Map.Entry<String, Integer> team2score)
            {
                return (team1score.getValue()).compareTo(team2score.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> teamsRanking = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : teamsScoreList) {
            teamsRanking.put(aa.getKey(), aa.getValue());
        }
        return teamsRanking;
    }
}
