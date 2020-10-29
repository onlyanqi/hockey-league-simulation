package simulation.state;

import simulation.model.NHLEvents;
import simulation.model.*;
import util.DateUtil;
import java.time.LocalDate;
import java.util.*;

public class InitializeSeasonState implements ISimulateState {

    private League league;
    private HockeyContext hockeyContext;
    private final Integer TotalGamesPerTeam = 82;
    private final Integer gamesPerTeamPerComponent = TotalGamesPerTeam / 3 - TotalGamesPerTeam % 3 ;

    public InitializeSeasonState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        InitializeRegularSeason();
        return exit();
    }

    private ISimulateState exit() {
        return new AdvanceTimeState(hockeyContext);
    }

    private void InitializeRegularSeason(){

        NHLEvents nhlEvents = new NHLEvents();

        LocalDate regularSeasonStartDate = nhlEvents.getRegularSeasonStartDate();
        LocalDate previousDateOfRegularSeasonStart = DateUtil.minusDays(nhlEvents.getRegularSeasonStartDate(), 1);
        LocalDate endDate = nhlEvents.getEndOfRegularSeason();

        int diffInDays = (int) DateUtil.diffDays(previousDateOfRegularSeasonStart,endDate);

        league.setCurrentDate(previousDateOfRegularSeasonStart);

        List<String> team_list_in_division = new ArrayList<>();
        List<String> team_list_out_division = new ArrayList<>();
        List<String> team_list_out_conference = new ArrayList<>();
        List<Game> tempGameList = new ArrayList<>();

        getGamesWithinDivision(team_list_in_division,tempGameList);
        getGamesWithinConference(team_list_out_division,tempGameList);
        getGamesAcrossLeague(team_list_out_conference,tempGameList);

        Games games = new Games();
        List<Game> gameList = games.getGameList();

        LocalDate currentDate = regularSeasonStartDate;

        Integer totalGamesAdded = tempGameList.size();

        scheduleGamesForGeneratedOnes(gameList,tempGameList,currentDate,totalGamesAdded,diffInDays);
        currentDate = DateUtil.addDays(currentDate,1);
        scheduleGamesForRemainingGeneratedOnes(gameList,tempGameList,currentDate,totalGamesAdded,diffInDays);

        TeamStanding regularSeasonTeamStanding = new TeamStanding();
        regularSeasonTeamStanding.initializeTeamStandingsRegularSeason(league);

        league.setRegularSeasonStanding(regularSeasonTeamStanding);
        league.setPlayOffStanding(new TeamStanding());
        league.setGames(games);
        league.setActiveTeamStanding(league.getRegularSeasonStanding());
        league.setNhlRegularSeasonEvents(nhlEvents);
    }

    private void scheduleGamesForRemainingGeneratedOnes(List<Game> gameList, List<Game> tempGameList, LocalDate currentDate, Integer totalGamesAdded, int diffInDays) {
        Random rand = new Random();
        for(int j=0;j<totalGamesAdded%diffInDays;j++){
            int randomNumber = rand.nextInt(tempGameList.size());
            Game game = tempGameList.get(randomNumber);
            game.setDate(currentDate);
            tempGameList.remove(randomNumber);
            gameList.add(game);
        }
    }

    private void scheduleGamesForGeneratedOnes(List<Game> gameList, List<Game> tempGameList, LocalDate currentDate, Integer totalGamesAdded, int diffInDays) {
        Random rand = new Random();
        for(int i=0;i<diffInDays;i++){
            currentDate = DateUtil.addDays(currentDate,1);
            for(int j=0;j<totalGamesAdded/diffInDays;j++){
                int randomNumber = rand.nextInt(tempGameList.size());
                Game game = tempGameList.get(randomNumber);
                game.setDate(currentDate);
                tempGameList.remove(randomNumber);
                gameList.add(game);
            }
        }
    }

    private void getGamesAcrossLeague(List<String> team_list_out_conference, List<Game> tempGameList) {
        for (Conference conf : league.getConferenceList()) {
            for (Division division : conf.getDivisionList()) {
                for (Team teamInLoop : division.getTeamList()) {
                    while (true) {
                        for (Conference conference2 : league.getConferenceList()) {
                            if ((conference2.getName().equals(conf.getName()))) continue;
                            for (Division division3 : conference2.getDivisionList()) {
                                for (Team team2InLoop : division3.getTeamList()) {
                                    if (checkMaxGamesReached(tempGameList, team2InLoop.getName(), gamesPerTeamPerComponent * 3))
                                        continue;
                                    if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), gamesPerTeamPerComponent * 3))
                                        continue;
                                    if (!(team_list_out_conference.contains(team2InLoop.getName()))) {
                                        Game game = new Game();
                                        game.setTeam1(teamInLoop.getName());
                                        game.setTeam2(team2InLoop.getName());
                                        tempGameList.add(game);
                                    }
                                }
                                if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), gamesPerTeamPerComponent * 3)) break;
                            }
                            if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), gamesPerTeamPerComponent * 3)) break;
                        }
                        team_list_out_conference.add(teamInLoop.getName());
                        if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), gamesPerTeamPerComponent * 3)) break;
                    }
                }
            }
        }
    }

    private void getGamesWithinConference(List<String> team_list_out_division, List<Game> tempGameList) {
        for (Conference conf : league.getConferenceList()) {
            for (Division division : conf.getDivisionList()) {
                for (Team teamInLoop : division.getTeamList()) {
                    while (true) {
                        for (Division division3 : conf.getDivisionList()) {
                            if ((division3.getName().equals(division.getName()))) continue;
                            for (Team team2InLoop : division3.getTeamList()) {
                                if (checkMaxGamesReached(tempGameList, team2InLoop.getName(), gamesPerTeamPerComponent * 2))
                                    continue;
                                if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), gamesPerTeamPerComponent * 2))
                                    continue;
                                if (!(team_list_out_division.contains(team2InLoop.getName()))) {
                                    Game game = new Game();
                                    game.setTeam1(teamInLoop.getName());
                                    game.setTeam2(team2InLoop.getName());
                                    tempGameList.add(game);
                                }
                            }
                            if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), gamesPerTeamPerComponent * 2)) break;
                        }
                        team_list_out_division.add(teamInLoop.getName());
                        if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), gamesPerTeamPerComponent * 2)) break;
                    }
                }
            }
        }
    }

    private void getGamesWithinDivision(List<String> team_list_in_division, List<Game> tempGameList) {
        for (Conference conf : league.getConferenceList()) {
            for (Division division : conf.getDivisionList()) {
                for (Team teamInLoop : division.getTeamList()) {
                    while (true) {
                        for (Team team2InLoop : division.getTeamList()) {
                            if ((teamInLoop.getName().equals(team2InLoop.getName()))) continue;
                            if (checkMaxGamesReached(tempGameList, team2InLoop.getName(), this.gamesPerTeamPerComponent)) continue;
                            if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), this.gamesPerTeamPerComponent)) continue;
                            if (!(team_list_in_division.contains(team2InLoop.getName()))) {
                                Game game = new Game();
                                game.setTeam1(teamInLoop.getName());
                                game.setTeam2(team2InLoop.getName());
                                System.out.println(teamInLoop.getName()+" & "+team2InLoop.getName());
                                tempGameList.add(game);
                            }
                        }
                        if (checkMaxGamesReached(tempGameList, teamInLoop.getName(), this.gamesPerTeamPerComponent)) break;
                    }
                    team_list_in_division.add(teamInLoop.getName());
                }
            }
        }
    }

    private Integer getTotalTeamsCount() {
        int teamsCount = 0;
        for(Conference conference2: league.getConferenceList()) {
            for (Division division : conference2.getDivisionList()) {
                for (Team team : division.getTeamList()) {
                    teamsCount = teamsCount + 1;
                }
            }
        }
        return teamsCount;
    }

    public static boolean checkMaxGamesReached(List<Game> gameList, String name, int count){
        int i =0;
        for(Game g: gameList){
            if(g.getTeam1().equals(name) ||g.getTeam2().equals(name)){
                i++;
            }
        }
        if(i >= count) return true;
        else return false;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

}
