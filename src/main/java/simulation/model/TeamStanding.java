package simulation.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TeamStanding implements ITeamStanding {

    private static final Logger log = Logger.getLogger(TeamStanding.class);
    private int id;
    private List<ITeamScore> teamsScoreList = new ArrayList<>();

    public TeamStanding() {
        teamsScoreList = new ArrayList<>();
        setId(System.identityHashCode(this));
    }

    public TeamStanding(simulation.serializers.ModelsForDeserialization.model.TeamStanding teamStanding) {
        this.id = teamStanding.id;
        for (simulation.serializers.ModelsForDeserialization.model.TeamScore teamScore : teamStanding.teamsScoreList) {
            this.teamsScoreList.add(new TeamScore(teamScore));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ITeamScore> getTeamsScoreList() {
        if (teamsScoreList == null) {
            log.error("Team Score list is empty.");
            throw new IllegalStateException("Team Score list is empty.");
        }
        return teamsScoreList;
    }

    public void setTeamsScoreList(List<ITeamScore> teamsScoreList) {
        this.teamsScoreList = teamsScoreList;
    }


    public void initializeTeamStandings(List<ITeam> teams) {
        if (teams == null) {
            log.error("Teams are empty to initialize team Standings");
            throw new IllegalStateException("Teams are empty to initialize team Standings");
        }
        int teamsSize = teams.size();
        teamsScoreList = new ArrayList<>(teamsSize);
        for (ITeam team : teams) {
            teamsScoreList.add(new TeamScore(team));
        }
    }

    public void initializeTeamStandingsRegularSeason(ILeague league) {
        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    teamsScoreList.add(new TeamScore(team));
                }
            }
        }
    }

    public void setTeamPoints(String teamName) {
        if (teamName == null) {
            log.error("Provided team is null. Unable to set team points");
            throw new IllegalArgumentException("Provided team is null. Unable to set team points");
        }
        for (ITeamScore teamScore : teamsScoreList) {
            if (teamScore.getTeam().getName().equals(teamName)) {
                int previousScore = teamScore.getPoints();
                int newTeamScore = previousScore + 2;
                teamScore.setPoints(newTeamScore);
            }
        }
    }

    public void setTeamWins(String teamName) {
        if (teamName == null) {
            log.error("Provided team is null. Unable to set team wins");
            throw new IllegalArgumentException("Provided team is null. Unable to set team wins");
        }
        for (ITeamScore teamScore : teamsScoreList) {
            if (teamScore.getTeam().getName().equals(teamName)) {
                int previousNumberOfWins = teamScore.getNumberOfWins();
                teamScore.setNumberOfWins(previousNumberOfWins + 1);
            }
        }
    }

    public void setTeamLoss(String teamName) {
        if (teamName == null) {
            log.error("Provided team is null. Unable to set team loss");
            throw new IllegalArgumentException("Provided team is null. Unable to set team loss");
        }
        for (ITeamScore teamScore : teamsScoreList) {
            if (teamScore.getTeam().getName().equals(teamName)) {
                int previousNumberOfLoss = teamScore.getNumberOfLoss();
                teamScore.setNumberOfLoss(previousNumberOfLoss + 1);
            }
        }
    }

    public void setTeamTies(String teamName) {
        if (teamName == null) {
            log.error("Provided team is null. Unable to set team ties");
            throw new IllegalArgumentException("Provided team is null. Unable to set team ties");
        }
        for (ITeamScore teamScore : teamsScoreList) {
            if (teamScore.getTeam().getName().equals(teamName)) {
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

    @Override
    public List<ITeamScore> getTeamsRankAcrossLeague(ILeague league) {
        List<ITeamScore> teamsScoreListLocal = this.teamsScoreList;
        List<ITeamScore> teamsScoreWithinLeague = new ArrayList<>();

        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    teamsScoreWithinLeague.add(getTeamScoreByTeamName(teamsScoreListLocal, team.getName()));
                }
            }
        }
        return sortTeamsScoreList(teamsScoreWithinLeague);
    }

    public List<ITeamScore> sortTeamsScoreList(List<ITeamScore> teamsScoreList) {
        teamsScoreList.sort((ITeamScore team1, ITeamScore team2) -> team2.getPoints().compareTo(team1.getPoints()));
        return teamsScoreList;
    }

    public ITeamScore getTeamScoreByTeamName(List<ITeamScore> teamsScoreList, String teamName) {
        return teamsScoreList.stream().filter(teamScore -> teamScore.getTeam().getName().equals(teamName)).findFirst().get();
    }

}
