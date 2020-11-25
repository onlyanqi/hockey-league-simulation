package simulation.model;

import java.util.ArrayList;
import java.util.List;

public class TeamStanding implements ITeamStanding {

    private int id;
    private List<ITeamScore> teamsScoreList;

    public TeamStanding() {
        teamsScoreList = new ArrayList<>();
        setId(System.identityHashCode(this));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ITeamScore> getTeamsScoreList() {
        return teamsScoreList;
    }

    public void setTeamsScoreList(List<ITeamScore> teamsScoreList) {
        this.teamsScoreList = teamsScoreList;
    }


    public void initializeTeamStandings(List<String> teamNames) {
        Integer teamsSize = teamNames.size();
        teamsScoreList = new ArrayList<>(teamsSize);
        for (String teamName : teamNames) {
            teamsScoreList.add(new TeamScore(teamName));
        }
    }

    public void initializeTeamStandingsRegularSeason(ILeague league) {
        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    teamsScoreList.add(new TeamScore(team.getName()));
                }
            }
        }
    }

    public void setTeamPoints(String teamName) {
        for (ITeamScore teamScore : teamsScoreList) {
            if (teamScore.getTeamName().equals(teamName)) {
                int previousScore = teamScore.getPoints();
                int newTeamScore = previousScore + 2;
                teamScore.setPoints(newTeamScore);
            }
        }
    }

    public void setTeamWins(String teamName) {
        for (ITeamScore teamScore : teamsScoreList) {
            if (teamScore.getTeamName().equals(teamName)) {
                int previousNumberOfWins = teamScore.getNumberOfWins();
                teamScore.setNumberOfWins(previousNumberOfWins + 1);
            }
        }
    }

    public void setTeamLoss(String teamName) {
        for (ITeamScore teamScore : teamsScoreList) {
            if (teamScore.getTeamName().equals(teamName)) {
                int previousNumberOfLoss = teamScore.getNumberOfLoss();
                teamScore.setNumberOfLoss(previousNumberOfLoss + 1);
            }
        }
    }

    public void setTeamTies(String teamName) {
        for (ITeamScore teamScore : teamsScoreList) {
            if (teamScore.getTeamName().equals(teamName)) {
                int previousNumberOfTies = teamScore.getNumberOfTies();
                teamScore.setNumberOfTies(previousNumberOfTies + 1);
            }
        }
    }

    public List<ITeamScore> getTeamsRankAcrossConference(ILeague league, String conferenceName) {
        List<ITeamScore> teamsScoreListLocal = this.teamsScoreList;
        List<ITeamScore> teamsScoreWithinConference = new ArrayList<>();
        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    if (conference.getName().equals(conferenceName)) {
                        teamsScoreWithinConference.add(getTeamScoreByTeamName(teamsScoreListLocal, team.getName()));
                    }
                }
            }
        }
        return sortTeamsScoreList(teamsScoreWithinConference);
    }

    public List<ITeamScore> getTeamsRankAcrossDivision(ILeague league, String divisionName) {
        List<ITeamScore> teamsScoreListLocal = this.teamsScoreList;
        List<ITeamScore> teamsScoreWithinDivision = new ArrayList<>();

        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    if (division.getName().equals(divisionName)) {
                        teamsScoreWithinDivision.add(getTeamScoreByTeamName(teamsScoreListLocal, team.getName()));
                    }
                }
            }
        }
        return sortTeamsScoreList(teamsScoreWithinDivision);
    }

    public List<ITeamScore> getTeamsRankAcrossLeague() {
        List<ITeamScore> teamsScoreListLocal = this.teamsScoreList;
        return sortTeamsScoreList(teamsScoreListLocal);
    }

        public List<ITeamScore> sortTeamsScoreList(List<ITeamScore> teamsScoreList) {
        teamsScoreList.sort((ITeamScore team1, ITeamScore team2) -> team2.getPoints().compareTo(team1.getPoints()));
        return teamsScoreList;
    }

    public ITeamScore getTeamScoreByTeamName(List<ITeamScore> teamsScoreList, String teamName) {
        return teamsScoreList.stream().filter(teamScore -> teamScore.getTeamName().equals(teamName)).findFirst().get();
    }

}
