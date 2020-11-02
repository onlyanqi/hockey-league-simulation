package simulation.state;

import simulation.model.NHLEvents;
import simulation.model.*;
import simulation.model.DateTime;
import java.time.LocalDate;
import java.util.*;

public class InitializeSeasonState implements ISimulateState {

    private League league;
    private HockeyContext hockeyContext;
    private final Integer TotalGamesPerTeam = 82;
    public InitializeSeasonState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        if(isMinimumTeamCountSatisfiedForPlayoffs(league)){
            InitializeRegularSeason();
        }else{
            System.out.println("Please make sure minimum number of teams(5) for each division are provided for the league");
            return null;
        }
        return exit();
    }

    private ISimulateState exit() {
        return new AdvanceTimeState(hockeyContext);
    }

    private void InitializeRegularSeason(){

        NHLEvents nhlEvents = new NHLEvents();

        LocalDate regularSeasonStartDate = nhlEvents.getRegularSeasonStartDate();
        LocalDate previousDateOfRegularSeasonStart = DateTime.minusDays(nhlEvents.getRegularSeasonStartDate(), 1);
        LocalDate endDate = nhlEvents.getEndOfRegularSeason();

        int diffInDays = (int) DateTime.diffDays(previousDateOfRegularSeasonStart,endDate);

        league.setCurrentDate(previousDateOfRegularSeasonStart);

        List<Game> tempGameList = new ArrayList<>();

        for(Conference conf: league.getConferenceList()){
            for(Division division : conf.getDivisionList()){
                for(Team team : division.getTeamList()) {
                    while (checkMaxGamesNotReachedForTeam(tempGameList, team)) {
                        addGamesWithinDivision(tempGameList,team,division);
                        addGamesOutsideDivision(tempGameList,team,division,conf);
                        addGamesOutsideConference(tempGameList,team,division,conf);
                    }
                }
            }
        }

        Games games = new Games();
        List<Game> gameList = games.getGameList();

        LocalDate currentDate = regularSeasonStartDate;

        Integer totalGamesAdded = tempGameList.size();

        scheduleGamesForGeneratedOnes(gameList,tempGameList,currentDate,totalGamesAdded,diffInDays);

        TeamStanding regularSeasonTeamStanding = new TeamStanding();
        regularSeasonTeamStanding.initializeTeamStandingsRegularSeason(league);

        league.setRegularSeasonStanding(regularSeasonTeamStanding);
        league.setPlayOffStanding(new TeamStanding());
        league.setGames(games);
        league.setActiveTeamStanding(league.getRegularSeasonStanding());
        league.setNhlRegularSeasonEvents(nhlEvents);
    }

    private Boolean isMinimumTeamCountSatisfiedForPlayoffs(League league) {
        for (Conference conference : league.getConferenceList()) {
            for (Division division : conference.getDivisionList()) {
                if(division.getTeamList().size() <=4){
                    return false;
                }
            }
        }
        return true;
    }

    private void addGamesOutsideConference(List<Game> tempGameList, Team team,Division division,Conference conf) {
        for (Conference otherConference : league.getConferenceList()) {
            if ((otherConference.getName().equals(conf.getName()))) continue;
            for (Division division3 : otherConference.getDivisionList()) {
                for (Team teamOutsideConference : division3.getTeamList()) {
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

    private void addGamesOutsideDivision(List<Game> tempGameList, Team team,Division division,Conference conf) {
        for (Division OtherDivision : conf.getDivisionList()) {
            if ((OtherDivision.getName().equals(division.getName()))) continue;
            for (Team teamOutsideDivision : OtherDivision.getTeamList()) {
                if (checkMaxGamesReached(tempGameList, team)) {
                    break;
                }
                if (checkMaxGamesReached(tempGameList, teamOutsideDivision)) {
                    continue;
                }
                Game game = new Game();
                game.setTeam1(team.getName());
                game.setTeam2(teamOutsideDivision.getName());
                tempGameList.add(game);
            }
        }
    }

    private void addGamesWithinDivision(List<Game> tempGameList, Team team,Division division) {
        for (Team teamInsideDivision : division.getTeamList()) {
            if ((team.getName().equals(teamInsideDivision.getName()))) continue;
            if (checkMaxGamesReached(tempGameList, team)) {
                break;
            }
            if (checkMaxGamesReached(tempGameList, teamInsideDivision)) {
                continue;
            }
            Game game = new Game();
            game.setTeam1(team.getName());
            game.setTeam2(teamInsideDivision.getName());
            tempGameList.add(game);
        }
    }

    private boolean checkMaxGamesReached(List<Game> gameList, Team team) {
        int teamCount =0;

        for(Game g: gameList){
            if(g.getTeam1().equals(team.getName()) ||g.getTeam2().equals(team.getName())){
                teamCount++;
            }
        }

        if(teamCount == TotalGamesPerTeam ){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean checkMaxGamesNotReachedForTeam(List<Game> gameList, Team team) {
        int teamCount =0;
        for(Game g: gameList){
            if(g.getTeam1().equals(team.getName()) ||g.getTeam2().equals(team.getName())){
                teamCount++;
            }
        }
        if(teamCount == TotalGamesPerTeam ){
            return false;
        }
        else{
            return true;
        }
    }


    private void scheduleGamesForGeneratedOnes(List<Game> gameList, List<Game> tempGameList, LocalDate currentDate, Integer totalGamesAdded, int diffInDays) {
        Random rand = new Random();
        for(int i=0;i<diffInDays;i++){
            for(int j=0;j<totalGamesAdded/diffInDays;j++){
                int randomNumber = rand.nextInt(tempGameList.size());
                Game game = tempGameList.get(randomNumber);
                game.setDate(currentDate);
                tempGameList.remove(randomNumber);
                gameList.add(game);
            }
            currentDate = DateTime.addDays(currentDate,1);
        }
        for(int j=0;j<totalGamesAdded%diffInDays;j++){
            int randomNumber = rand.nextInt(tempGameList.size());
            Game game = tempGameList.get(randomNumber);
            game.setDate(currentDate);
            tempGameList.remove(randomNumber);
            gameList.add(game);
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

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

}
