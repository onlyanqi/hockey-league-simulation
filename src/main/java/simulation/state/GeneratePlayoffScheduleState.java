package simulation.state;

import org.apache.log4j.Logger;
import simulation.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneratePlayoffScheduleState implements ISimulateState {

    private Logger log = Logger.getLogger(GeneratePlayoffScheduleState.class);
    private final Integer numberOfGamesPerTeam = 7;
    private final Integer numberOfTeamStandingBeforeStanleyCup = 4;
    private HockeyContext hockeyContext;
    private League league;
    private NHLEvents nhlEvents;
    private GameSchedule games;
    private TeamStanding teamStanding;

    public GeneratePlayoffScheduleState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.nhlEvents = league.getNHLRegularSeasonEvents();
        this.games = league.getGames();
        this.teamStanding = league.getActiveTeamStanding();
    }

    @Override
    public ISimulateState action() {
        if (nhlEvents.checkEndOfRegularSeason(league.getCurrentDate())) {
            generatePlayOffFirstRoundSchedule();
            log.info("PlayOff First Round is Scheduled");
        } else if (games.doGamesDoesNotExistOnOrAfterDate(league.getCurrentDate())) {
            if (teamStanding.getTeamsScoreList().size() == numberOfTeamStandingBeforeStanleyCup) {
                generateStanleyCupSchedule();
                log.info("Stanley Cup is Scheduled");
            } else {
                generatePlayOffSecondAndThirdRoundSchedule();
                log.info("Second or Third round of Stanley cup is scheduled");
            }
        }
        return exit();
    }

    private void generatePlayOffFirstRoundSchedule() {
        TeamStanding regularSeasonStanding = league.getRegularSeasonStanding();
        league.setActiveTeamStanding(league.getPlayOffStanding());

        HashMap<String, List<String>> playOffTeams = new HashMap<>();
        for (Conference conference : league.getConferenceList()) {
            List<String> teams = new ArrayList<>();
            for (Division division : conference.getDivisionList()) {
                List<TeamScore> teamsScoreWithinDivision = regularSeasonStanding.getTeamsRankAcrossDivision(league, division.getName());
                teams.add(teamsScoreWithinDivision.get(0).getTeamName());
                teams.add(teamsScoreWithinDivision.get(1).getTeamName());
                teams.add(teamsScoreWithinDivision.get(2).getTeamName());
            }
            List<TeamScore> teamsScoreWithinConference = regularSeasonStanding.getTeamsRankAcrossConference(league, conference.getName());
            teams.add(teamsScoreWithinConference.get(6).getTeamName());
            teams.add(teamsScoreWithinConference.get(7).getTeamName());
            playOffTeams.put(conference.getName(), teams);
        }

        initializeGamesPlayOffFirstRound(playOffTeams);
        initializeTeamStandingsFirstRound(playOffTeams);

    }

    private void initializeTeamStandingsFirstRound(HashMap<String, List<String>> playOffTeams) {
        List<String> teamsAcrossConferences = new ArrayList<>();
        for (Conference conference : league.getConferenceList()) {
            List<String> teams = playOffTeams.get(conference.getName());
            teamsAcrossConferences.addAll(teams);
        }
        league.getActiveTeamStanding().initializeTeamStandings(teamsAcrossConferences);
    }

    private void initializeGamesPlayOffFirstRound(HashMap<String, List<String>> playOffTeams) {
        LocalDate playOffStartDate = nhlEvents.getPlayOffStartDate();
        List<Game> games = this.games.getGameList();
        for (Conference conference : league.getConferenceList()) {
            List<String> teams = playOffTeams.get(conference.getName());
            scheduleGameBetweenTeams(teams.get(0), teams.get(7), games, playOffStartDate);
            scheduleGameBetweenTeams(teams.get(1), teams.get(2), games, playOffStartDate);
            scheduleGameBetweenTeams(teams.get(3), teams.get(6), games, DateTime.addDays(playOffStartDate, 1));
            scheduleGameBetweenTeams(teams.get(4), teams.get(5), games, DateTime.addDays(playOffStartDate, 1));
        }
    }

    private void scheduleGameBetweenTeams(String team1, String team2, List<Game> games, LocalDate startDate) {
        LocalDate currentDate = startDate;
        for (Integer i = 0; i < numberOfGamesPerTeam; i++) {
            Game game = new Game();
            game.setTeam1(team1);
            game.setTeam2(team2);
            game.setDate(currentDate);
            games.add(game);
            currentDate = DateTime.addDays(currentDate, 2);
        }
    }

    private void generatePlayOffSecondAndThirdRoundSchedule() {
        List<TeamScore> teamScoreList = teamStanding.getTeamsScoreList();
        updateTeamStanding(teamStanding, teamScoreList);
    }

    private void updateTeamStanding(TeamStanding teamStanding, List<TeamScore> teamScoreList) {
        List<String> qualifiedTeams = new ArrayList<>();
        for (Integer i = 0; i < teamScoreList.size(); i = i + 2) {
            if (declareTeam1Winner(teamScoreList.get(i), teamScoreList.get(i + 1))) {
                qualifiedTeams.add(teamScoreList.get(i).getTeamName());
            } else {
                qualifiedTeams.add(teamScoreList.get(i + 1).getTeamName());
            }
        }
        initializeGamesPlayOff(qualifiedTeams);
        teamStanding.initializeTeamStandings(qualifiedTeams);
    }

    private void initializeGamesPlayOff(List<String> qualifiedTeams) {
        for (Integer i = 0; i < qualifiedTeams.size(); i = i + 2) {
            scheduleGameBetweenTeams(qualifiedTeams.get(i), qualifiedTeams.get(i + 1), games.getGameList(), league.getCurrentDate());
        }
    }

    private Boolean declareTeam1Winner(TeamScore teamScore1, TeamScore teamScore2) {
        if (teamScore1.getPoints() > teamScore2.getPoints()) {
            return true;
        } else {
            return false;
        }
    }

    private void generateStanleyCupSchedule() {
        List<TeamScore> teamScoreList = league.getActiveTeamStanding().getTeamsScoreList();
        List<String> qualifiedTeams = new ArrayList<>();
        for (Integer i = 0; i < teamScoreList.size(); i = i + 2) {
            if (declareTeam1Winner(teamScoreList.get(i), teamScoreList.get(i + 1))) {
                qualifiedTeams.add(teamScoreList.get(i).getTeamName());
            } else {
                qualifiedTeams.add(teamScoreList.get(i + 1).getTeamName());
            }
        }
        initializeGamesPlayOff(qualifiedTeams);
        teamStanding.initializeTeamStandings(qualifiedTeams);
    }

    public ISimulateState exit() {
        return new TrainingState(hockeyContext);
    }
}
