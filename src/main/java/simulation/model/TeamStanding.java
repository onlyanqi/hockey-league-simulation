package simulation.model;

import java.util.ArrayList;
import java.util.List;

public class TeamStanding {

    public TeamStanding() {
        teamsScoreList = new ArrayList<>();
    }

    private int id;
    private List<TeamScore> teamsScoreList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TeamScore> getTeamsScoreList() {
        return teamsScoreList;
    }

    public void setTeamsScoreList(List<TeamScore> teamsScoreList) {
        this.teamsScoreList = teamsScoreList;
    }


    public void initializeTeamStandings(List<String> teamNames){
        Integer teamsSize = teamNames.size();
        teamsScoreList = new ArrayList<>(teamsSize);
        for(String teamName : teamNames){
            teamsScoreList.add(new TeamScore(teamName));
        }
    }

    public void initializeTeamStandingsRegularSeason(League league){
        for(Conference conference: league.getConferenceList() ){
            for(Division division: conference.getDivisionList()){
                for(Team team: division.getTeamList()){
                    teamsScoreList.add(new TeamScore(team.getName()));
                }
            }
        }
    }

    public void setTeamPoints(String teamName){
        for(TeamScore teamScore: teamsScoreList){
            if(teamScore.getTeamName().equals(teamName)){
                int previousScore = teamScore.getPoints();
                int newTeamScore = previousScore + 2;
                teamScore.setPoints(newTeamScore);
            }
        }
    }

    public void setTeamWins(String teamName){
        for(TeamScore teamScore: teamsScoreList){
            if(teamScore.getTeamName().equals(teamName)){
                int previousNumberOfWins = teamScore.getNumberOfWins();
                teamScore.setNumberOfWins(previousNumberOfWins + 1);
                setTeamPoints(teamScore.getTeamName());
            }
        }
    }

    public void setTeamLoss(String teamName){
        for(TeamScore teamScore: teamsScoreList){
            if(teamScore.getTeamName().equals(teamName)){
                int previousNumberOfLoss = teamScore.getNumberOfLoss();
                teamScore.setNumberOfLoss(previousNumberOfLoss + 1);
            }
        }
    }

    public void setTeamTies(String teamName){
        for(TeamScore teamScore: teamsScoreList){
            if(teamScore.getTeamName().equals(teamName)){
                int previousNumberOfTies = teamScore.getNumberOfTies();
                teamScore.setNumberOfTies(previousNumberOfTies + 1);
            }
        }
    }

    public List<TeamScore> getTeamsRankAcrossConference(League league, String conferenceName){
        List<TeamScore> teamsScoreListLocal =  this.teamsScoreList;
        List<TeamScore> teamsScoreWithinConference = new ArrayList<>();
        for(Conference conference: league.getConferenceList() ){
            for(Division division: conference.getDivisionList()){
                for(Team team: division.getTeamList()){
                    if(conference.getName().equals(conferenceName)){
                        teamsScoreWithinConference.add(getTeamScoreByTeamName(teamsScoreListLocal,team.getName()));
                    }
                }
            }
        }
        return sortTeamsScoreList(teamsScoreWithinConference);
    }

    public List<TeamScore> getTeamsRankAcrossDivision(League league, String divisionName){
        List<TeamScore> teamsScoreListLocal =  this.teamsScoreList;
        List<TeamScore> teamsScoreWithinDivision = new ArrayList<>();

        for(Conference conference: league.getConferenceList() ){
            for(Division division: conference.getDivisionList()){
                for(Team team: division.getTeamList()){
                    if(division.getName().equals(divisionName)){
                        teamsScoreWithinDivision.add(getTeamScoreByTeamName(teamsScoreListLocal,team.getName()));
                    }
                }
            }
        }
        return sortTeamsScoreList(teamsScoreWithinDivision);
    }

    public List<TeamScore> sortTeamsScoreList(List<TeamScore> teamsScoreList){
        teamsScoreList.sort((TeamScore team1,TeamScore team2) ->team1.getPoints().compareTo(team2.getPoints()));
        return teamsScoreList;
    }

    public TeamScore getTeamScoreByTeamName(List<TeamScore> teamsScoreList,String teamName){
       return teamsScoreList.stream().filter(teamScore -> teamScore.getTeamName().equals(teamName)).findFirst().get();
    }

}
