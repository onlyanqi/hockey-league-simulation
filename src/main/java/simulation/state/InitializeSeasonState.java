package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import simulation.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class InitializeSeasonState implements ISimulateState {

    private static Logger log = Logger.getLogger(InitializeSeasonState.class);
    private final Integer TotalGamesPerTeam = 82;
    private final Integer minimumTeamCountForPlayOffs = 5;
    private ILeague league;
    private IHockeyContext hockeyContext;

    public InitializeSeasonState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        if (isMinimumTeamCountSatisfiedForPlayoffs(league)) {
            InitializeRegularSeason();
            log.info("Initialized regular season for  " + league.getCurrentDate().getYear());
        } else {
            log.error("Please make sure minimum number of teams(" + minimumTeamCountForPlayOffs + ") for each division are provided to the league");
            ConsoleOutput.getInstance().printMsgToConsole("Please make sure minimum number of teams(" + minimumTeamCountForPlayOffs + ") for each division are provided to the league");
            return null;
        }
        return exit();
    }

    public ISimulateState exit() {
        return new AdvanceTimeState(hockeyContext);
    }

    public void InitializeRegularSeason() {

        IModelFactory modelFactory = hockeyContext.getModelFactory();
        INHLEvents nhlEvents = modelFactory.createNHLEventsByYear(league.getCurrentDate().getYear());
        LocalDate regularSeasonStartDate = nhlEvents.getRegularSeasonStartDate();
        LocalDate previousDateOfRegularSeasonStart = DateTime.minusDays(nhlEvents.getRegularSeasonStartDate(), 1);
        LocalDate endDate = nhlEvents.getEndOfRegularSeason();
        IGameSchedule games = modelFactory.createGameSchedule();
        ITeamStanding regularSeasonTeamStanding = modelFactory.createTeamStanding();
        List<IGame> tempGameList = new ArrayList<>();
        int diffInDays = (int) DateTime.diffDays(previousDateOfRegularSeasonStart, endDate);

        league.setCurrentDate(previousDateOfRegularSeasonStart);

        for (IConference conf : league.getConferenceList()) {
            for (IDivision division : conf.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    while (checkMaxGamesNotReachedForTeam(tempGameList, team)) {
                        addGamesWithinDivision(tempGameList, team, division);
                        addGamesOutsideDivision(tempGameList, team, division, conf);
                        addGamesOutsideConference(tempGameList, team, division, conf);
                    }
                }
            }
        }

        List<IGame> gameList = games.getGameList();
        LocalDate currentDate = regularSeasonStartDate;
        Integer totalGamesAdded = tempGameList.size();

        scheduleGamesForGeneratedOnes(gameList, tempGameList, currentDate, totalGamesAdded, diffInDays);
        regularSeasonTeamStanding.initializeTeamStandingsRegularSeason(league);

        league.setRegularSeasonStanding(regularSeasonTeamStanding);
        league.setPlayOffStanding(hockeyContext.getModelFactory().createTeamStanding());
        league.setGames(games);
        league.setActiveTeamStanding(league.getRegularSeasonStanding());
        league.setStanleyCupFinalsTeamScores(new HashMap<>());
        league.setNhlRegularSeasonEvents(nhlEvents);
        league.initializeTeamStats();
    }

    private Boolean isMinimumTeamCountSatisfiedForPlayoffs(ILeague league) {
        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                if (division.getTeamList().size() < minimumTeamCountForPlayOffs) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addGamesOutsideConference(List<IGame> tempGameList, ITeam team, IDivision division, IConference conf) {
        for (IConference otherConference : league.getConferenceList()) {
            if ((otherConference.getName().equals(conf.getName()))) continue;
            for (IDivision division3 : otherConference.getDivisionList()) {
                for (ITeam teamOutsideConference : division3.getTeamList()) {
                    if (checkMaxGamesReached(tempGameList, team)) {
                        break;
                    }
                    if (checkMaxGamesReached(tempGameList, teamOutsideConference)) {
                        continue;
                    }
                    Game game = new Game();
                    game.setTeam1(team.getName());
                    game.setTeam2(teamOutsideConference.getName());
                    tempGameList.add(game);
                }
            }
        }
    }

    private void addGamesOutsideDivision(List<IGame> tempGameList, ITeam team, IDivision division, IConference conf) {
        for (IDivision OtherDivision : conf.getDivisionList()) {
            if ((OtherDivision.getName().equals(division.getName()))) continue;
            for (ITeam teamOutsideDivision : OtherDivision.getTeamList()) {
                if (checkMaxGamesReached(tempGameList, team)) {
                    break;
                }
                if (checkMaxGamesReached(tempGameList, teamOutsideDivision)) {
                    continue;
                }
                IModelFactory gameFactory = hockeyContext.getModelFactory();
                IGame game = gameFactory.createGame();
                game.setTeam1(team.getName());
                game.setTeam2(teamOutsideDivision.getName());
                tempGameList.add(game);
            }
        }
    }

    private void addGamesWithinDivision(List<IGame> tempGameList, ITeam team, IDivision division) {
        for (ITeam teamInsideDivision : division.getTeamList()) {
            if ((team.getName().equals(teamInsideDivision.getName()))) continue;
            if (checkMaxGamesReached(tempGameList, team)) {
                break;
            }
            if (checkMaxGamesReached(tempGameList, teamInsideDivision)) {
                continue;
            }
            IModelFactory factory = hockeyContext.getModelFactory();
            IGame game = factory.createGame();
            game.setTeam1(team.getName());
            game.setTeam2(teamInsideDivision.getName());
            tempGameList.add(game);
        }
    }

    private boolean checkMaxGamesReached(List<IGame> gameList, ITeam team) {
        int teamCount = 0;
        for (IGame g : gameList) {
            if (g.getTeam1().equals(team.getName()) || g.getTeam2().equals(team.getName())) {
                teamCount++;
            }
        }
        if (teamCount == TotalGamesPerTeam) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkMaxGamesNotReachedForTeam(List<IGame> gameList, ITeam team) {
        int teamCount = 0;
        for (IGame g : gameList) {
            if (g.getTeam1().equals(team.getName()) || g.getTeam2().equals(team.getName())) {
                teamCount++;
            }
        }
        if (teamCount == TotalGamesPerTeam) {
            return false;
        } else {
            return true;
        }
    }


    private void scheduleGamesForGeneratedOnes(List<IGame> gameList, List<IGame> tempGameList,
                                               LocalDate currentDate, Integer totalGamesAdded, int diffInDays) {
        Random rand = new Random();
        for (int i = 0; i < diffInDays; i++) {
            List<IGame> gameListOnDay = new ArrayList<>();
            for (int j = 0; j < totalGamesAdded / diffInDays; j++) {
                int randomNumber = rand.nextInt(tempGameList.size());
                IGame game = tempGameList.get(randomNumber);
                while (teamExistsOnDay(gameListOnDay, game)) {
                    randomNumber = rand.nextInt(tempGameList.size());
                    game = tempGameList.get(randomNumber);
                }
                game.setDate(currentDate);
                tempGameList.remove(randomNumber);
                gameList.add(game);
                gameListOnDay.add(game);
            }
            gameListOnDay.clear();
            currentDate = DateTime.addDays(currentDate, 1);
        }
        List<IGame> gameListOnDay = new ArrayList<>();
        for (int j = 0; j < totalGamesAdded % diffInDays; j++) {
            int randomNumber = rand.nextInt(tempGameList.size());
            IGame game = tempGameList.get(randomNumber);
            while (teamExistsOnDay(gameListOnDay, game)) {
                randomNumber = rand.nextInt(tempGameList.size());
                game = tempGameList.get(randomNumber);
            }
            game.setDate(currentDate);
            tempGameList.remove(randomNumber);
            gameList.add(game);
        }
    }

    private boolean teamExistsOnDay(List<IGame> gameList, IGame game) {
        for (IGame gameFromList : gameList) {
            if (gameFromList.getTeam1().equals(game.getTeam1()) || gameFromList.getTeam1().equals(game.getTeam2()) || gameFromList.getTeam2().equals(game.getTeam1()) || gameFromList.getTeam2().equals(game.getTeam2())) {
                return true;
            }
        }
        return false;
    }


    public ILeague getLeague() {
        return league;
    }

    public void setLeague(ILeague league) {
        this.league = league;
    }

}
