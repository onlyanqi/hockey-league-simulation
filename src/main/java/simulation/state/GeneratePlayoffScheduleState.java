package simulation.state;

import org.apache.log4j.Logger;
import simulation.model.*;

import java.time.LocalDate;
import java.util.*;

public class GeneratePlayoffScheduleState implements ISimulateState {

    private static Logger log = Logger.getLogger(GeneratePlayoffScheduleState.class);
    private final Integer numberOfGamesPerTeam = 7;
    private final Integer numberOfTeamStandingBeforeStanleyCup = 4;
    private IHockeyContext hockeyContext;
    private ILeague league;
    private INHLEvents nhlEvents;
    private IGameSchedule games;
    private ITeamStanding teamStanding;
    private HashMap<ITeam, Integer> stanleyCupTeamStanding;

    public GeneratePlayoffScheduleState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.nhlEvents = league.getNHLRegularSeasonEvents();
        this.games = league.getGames();
        this.teamStanding = league.getActiveTeamStanding();
        this.stanleyCupTeamStanding = league.getStanleyCupFinalsTeamScores();
    }

    @Override
    public ISimulateState action() {
        if (nhlEvents.checkEndOfRegularSeason(league.getCurrentDate())) {
            generatePlayOffFirstRoundSchedule();
            log.info("Generated PlayOff Schedule for first Round for season" + (league.getCurrentDate().getYear() - 1));
        } else if (games.doGamesDoesNotExistOnOrAfterDate(league.getCurrentDate())) {
            if (teamStanding.getTeamsScoreList().size() == numberOfTeamStandingBeforeStanleyCup) {
                generateStanleyCupSchedule();
                log.info("Generated Stanley Cup Schedule" + (league.getCurrentDate().getYear() - 1));
            } else {
                generatePlayOffSecondAndThirdRoundSchedule();
                log.info("Generated PlayOff Schedule for second or third round" + (league.getCurrentDate().getYear() - 1));
            }
        }
        return exit();
    }

    private void generatePlayOffFirstRoundSchedule() {
        ITeamStanding regularSeasonStanding = league.getRegularSeasonStanding();
        if (regularSeasonStanding == null) {
            log.error("Regular season standing is null.");
            throw new IllegalStateException("Regular team standing is null while generating playoff standing");
        }
        league.setActiveTeamStanding(league.getPlayOffStanding());

        Map<String, List<ITeam>> playOffTeams = new HashMap<>();
        for (IConference conference : league.getConferenceList()) {
            List<ITeam> teams = new ArrayList<>();
            for (IDivision division : conference.getDivisionList()) {
                List<ITeamScore> teamsScoreWithinDivision = regularSeasonStanding.getTeamsRankAcrossDivision(league, division.getName());
                teams.add(teamsScoreWithinDivision.get(0).getTeam());
                teams.add(teamsScoreWithinDivision.get(1).getTeam());
                teams.add(teamsScoreWithinDivision.get(2).getTeam());
            }
            List<ITeamScore> teamsScoreWithinConference = regularSeasonStanding.getTeamsRankAcrossConference(league, conference.getName());

            Iterator<ITeamScore> iteratorTeamScore = teamsScoreWithinConference.iterator();
            while (iteratorTeamScore.hasNext()) {
                ITeamScore teamScore = iteratorTeamScore.next();
                if (teams.contains(teamScore.getTeam())) {
                    iteratorTeamScore.remove();
                }
            }

            teams.add(teamsScoreWithinConference.get(0).getTeam());
            teams.add(teamsScoreWithinConference.get(1).getTeam());
            playOffTeams.put(conference.getName(), teams);
        }

        initializeGamesPlayOffFirstRound(playOffTeams);
        initializeTeamStandingsFirstRound(playOffTeams);

    }

    private void initializeTeamStandingsFirstRound(Map<String, List<ITeam>> playOffTeams) {
        if (playOffTeams == null) {
            log.error("Play off teams is null.");
            throw new IllegalStateException("Play off teams is null.");
        }
        List<ITeam> teamsAcrossConferences = new ArrayList<>();
        for (IConference conference : league.getConferenceList()) {
            List<ITeam> teams = playOffTeams.get(conference.getName());
            teamsAcrossConferences.addAll(teams);
        }
        league.getActiveTeamStanding().initializeTeamStandings(teamsAcrossConferences);
    }

    private void initializeGamesPlayOffFirstRound(Map<String, List<ITeam>> playOffTeams) {
        if (playOffTeams == null) {
            log.error("Play off teams is null.");
            throw new IllegalStateException("Play off teams is null.");
        }
        LocalDate playOffStartDate = nhlEvents.getPlayOffStartDate();
        List<IGame> games = this.games.getGameList();
        for (IConference conference : league.getConferenceList()) {
            List<ITeam> teams = playOffTeams.get(conference.getName());
            scheduleGameBetweenTeams(teams.get(0).getName(), teams.get(7).getName(), games, playOffStartDate);
            scheduleGameBetweenTeams(teams.get(1).getName(), teams.get(2).getName(), games, playOffStartDate);
            scheduleGameBetweenTeams(teams.get(3).getName(), teams.get(6).getName(), games, DateTime.addDays(playOffStartDate, 1));
            scheduleGameBetweenTeams(teams.get(4).getName(), teams.get(5).getName(), games, DateTime.addDays(playOffStartDate, 1));
        }
    }

    private void scheduleGameBetweenTeams(String team1, String team2, List<IGame> games, LocalDate startDate) {
        if (team1 == null || team2 == null) {
            log.error("Either of the teams are null to schedule games.");
            throw new IllegalArgumentException("Either of the teams are null to schedule games.");
        }
        LocalDate currentDate = startDate;
        for (Integer i = 0; i < numberOfGamesPerTeam; i++) {
            IModelFactory gameFactory = hockeyContext.getModelFactory();
            IGame game = gameFactory.createGame();
            game.setTeam1(team1);
            game.setTeam2(team2);
            game.setDate(currentDate);
            games.add(game);
            currentDate = DateTime.addDays(currentDate, 2);
        }
    }

    private void generatePlayOffSecondAndThirdRoundSchedule() {
        List<ITeamScore> teamScoreList = teamStanding.getTeamsScoreList();
        for (ITeamScore teamScore : teamScoreList) {
            stanleyCupTeamStanding.put(teamScore.getTeam(), teamScore.getPoints());
        }
        updateTeamStanding(teamStanding, teamScoreList);
    }

    private void updateTeamStanding(ITeamStanding teamStanding, List<ITeamScore> teamScoreList) {
        List<ITeam> qualifiedTeams = new ArrayList<>();
        for (int i = 0; i < teamScoreList.size(); i = i + 2) {
            if (declareTeam1Winner(teamScoreList.get(i), teamScoreList.get(i + 1))) {
                qualifiedTeams.add(teamScoreList.get(i).getTeam());
            } else {
                qualifiedTeams.add(teamScoreList.get(i + 1).getTeam());
            }
        }
        initializeGamesPlayOff(qualifiedTeams);
        teamStanding.initializeTeamStandings(qualifiedTeams);
    }

    private void initializeGamesPlayOff(List<ITeam> qualifiedTeams) {
        for (int i = 0; i < qualifiedTeams.size(); i = i + 2) {
            scheduleGameBetweenTeams(qualifiedTeams.get(i).getName(), qualifiedTeams.get(i + 1).getName(), games.getGameList(), league.getCurrentDate());
        }
    }

    private Boolean declareTeam1Winner(ITeamScore teamScore1, ITeamScore teamScore2) {
        if (teamScore1.getPoints() > teamScore2.getPoints()) {
            return true;
        } else {
            return false;
        }
    }

    private void generateStanleyCupSchedule() {
        List<ITeamScore> teamScoreList = league.getActiveTeamStanding().getTeamsScoreList();
        List<ITeam> qualifiedTeams = new ArrayList<>();
        for (ITeamScore teamScore : teamScoreList) {
            stanleyCupTeamStanding.put(teamScore.getTeam(), stanleyCupTeamStanding.get(teamScore.getTeam()) + teamScore.getPoints());
        }
        for (int i = 0; i < teamScoreList.size(); i = i + 2) {
            if (declareTeam1Winner(teamScoreList.get(i), teamScoreList.get(i + 1))) {
                qualifiedTeams.add(teamScoreList.get(i).getTeam());
            } else {
                qualifiedTeams.add(teamScoreList.get(i + 1).getTeam());
            }
        }
        initializeGamesPlayOff(qualifiedTeams);
        teamStanding.initializeTeamStandings(qualifiedTeams);
    }

    public ISimulateState exit() {
        return new TrainingState(hockeyContext);
    }
}
