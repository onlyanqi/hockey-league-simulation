package simulation.state;

import simulation.model.*;
import util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InitializeSeasonState implements ISimulateState {

    private League league;
    private final String startDate = "2020-09-30";
    private final int TotalGamesPerUser = 82;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public InitializeSeasonState(HockeyContext hockeyContext) {
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        try {
            InitializeRegularSeason();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Unable to parse date. Please contact admin.");
            System.exit(1);
        }
        return exit();
    }

    private ISimulateState exit() {
        return new AdvanceTimeState();
    }

    private void InitializeRegularSeason() throws ParseException {
        Integer TotalTeams = getTotalTeamsCount();
        Integer TotalGames = (TotalGamesPerUser * TotalTeams) /2;

        List<String> team_list_in_division = new ArrayList<>();
        List<String> team_list_out_division = new ArrayList<>();
        List<String> team_list_out_conference = new ArrayList<>();
        List<Game> gameList = new ArrayList<>();
        for(Conference conf: league.getConferenceList()) {
            for (Division division : conf.getDivisionList()) {
                for (Team teamInLoop : division.getTeamList()) {

                    while (true) {
                        for (Team team2InLoop : division.getTeamList()) {
                            if ((teamInLoop.getName().equals(team2InLoop.getName()))) continue;
                            if (!checkMaxGamesReached(gameList, team2InLoop.getName(),TotalGamesPerUser/3)) continue;
                            if (!checkMaxGamesReached(gameList, teamInLoop.getName(),TotalGamesPerUser/3)) continue;
                            if (!(team_list_in_division.contains(team2InLoop.getName()))) {
                                Game game = new Game();
                                game.setTeam1(teamInLoop.getName());
                                game.setTeam2(team2InLoop.getName());
                                gameList.add(game);
                            }

                        }
                        if (!checkMaxGamesReached(gameList, teamInLoop.getName(),TotalGamesPerUser/3)) break;
                    }
                    team_list_in_division.add(teamInLoop.getName());
                }
            }
        }

        for(Conference conf: league.getConferenceList()) {
            for (Division division : conf.getDivisionList()) {
                for (Team teamInLoop : division.getTeamList()) {
                    while(true) {
                        for (Division division3 : conf.getDivisionList()) {
                            if ((division3.getName().equals(division.getName()))) continue;
                            for (Team team2InLoop : division3.getTeamList()) {
                                if (!checkMaxGamesReached(gameList, team2InLoop.getName(), TotalGamesPerUser*2/3)) continue;
                                if (!checkMaxGamesReached(gameList, teamInLoop.getName(), TotalGamesPerUser*2/3)) continue;
                                if (!(team_list_out_division.contains(team2InLoop.getName()))) {
                                    Game game = new Game();
                                    game.setTeam1(teamInLoop.getName());
                                    game.setTeam2(team2InLoop.getName());
                                    gameList.add(game);
                                }
                            }
                            if (!checkMaxGamesReached(gameList, teamInLoop.getName(), TotalGamesPerUser*2/3)) break;
                        }
                        team_list_out_division.add(teamInLoop.getName());
                        if (!checkMaxGamesReached(gameList, teamInLoop.getName(), TotalGamesPerUser*2/3)) break;
                    }
                }
            }
        }

        for(Conference conf: league.getConferenceList()) {
            for (Division division : conf.getDivisionList()) {
                for (Team teamInLoop : division.getTeamList()) {
                    while(true) {
                        for (Conference conference2 : league.getConferenceList()) {
                            if ((conference2.getName().equals(conf.getName()))) continue;
                            for (Division division3 : conference2.getDivisionList()) {
                                for (Team team2InLoop : division3.getTeamList()) {
                                    if (!checkMaxGamesReached(gameList, team2InLoop.getName(), TotalGamesPerUser)) continue;
                                    if (!checkMaxGamesReached(gameList, teamInLoop.getName(), TotalGamesPerUser)) continue;
                                    if (!(team_list_out_conference.contains(team2InLoop.getName()))) {
                                        Game game = new Game();
                                        game.setTeam1(teamInLoop.getName());
                                        game.setTeam2(team2InLoop.getName());
                                        gameList.add(game);
                                    }
                                }
                                if (!checkMaxGamesReached(gameList, teamInLoop.getName(), TotalGamesPerUser)) break;
                            }
                            if(!checkMaxGamesReached(gameList, teamInLoop.getName(), TotalGamesPerUser)) break;
                        }
                        team_list_out_conference.add(teamInLoop.getName());
                        if (!checkMaxGamesReached(gameList, teamInLoop.getName(), TotalGamesPerUser)) break;
                    }
                }
            }
        }

        for(Conference conference2: league.getConferenceList())
            for(Division division: conference2.getDivisionList())
                for(Team team : division.getTeamList()){
                    int i =0;
                    for(Game g: gameList){
                        if(g.getTeam1().equals(team.getName()) || g.getTeam2().equals(team.getName())  ){
                            i++;
                        }
                    }
                    System.out.println("Team " + team.getName()+" count is "+ i);
                }

        //Hey Mani, delete timestamp in the date thing :)

        String dateStop = "2021-04-04";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = DateUtil.addDays(format.parse(startDate), 1);
        Date endDate = format.parse(dateStop);

        int diffInDays = (int) ((endDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24));

        Games games = new Games();
        List<Game> gameList1 = games.getGameList();
        Random rand = new Random();

        //Hey Mani, also make sure a team wont play more than one game on same day.
        for(int i=0;i<diffInDays;i++){
            currentDate = DateUtil.addDays(currentDate,1);
            for(int j=0;j<TotalGames/diffInDays;j++){
                int randomNumber = rand.nextInt(gameList.size());
                Game game = gameList.get(randomNumber);
                game.setDate(currentDate);
                gameList.remove(randomNumber);
                gameList1.add(game);
            }
        }
        currentDate = DateUtil.addDays(currentDate,1);
        for(int j=0;j<TotalGames%diffInDays;j++){
            int randomNumber = rand.nextInt(gameList.size());
            Game game = gameList.get(randomNumber);
            game.setDate(currentDate);
            gameList.remove(randomNumber);
            gameList1.add(game);
        }

//        ScoreBoard scoreBoard = new ScoreBoard();
//        scoreBoard.initializeScore(league);
//        for(GameSchedule gameSchedule: gs){
//            Game g = gameSchedule.getGame();
//            HashMap<String,Integer> teamScores =  scoreBoard.getTeamScore();
//            if(simulateGame(g)==1){
//                gameSchedule.setResult(simulateGame(g));
//                int previousScore = teamScores.get(g.getTeam1());
//                teamScores.put(g.getTeam1(),previousScore + 2);
//                scoreBoard.setTeamScore(teamScores);
//            }else{
//                gameSchedule.setResult(simulateGame(g));
//                int previousScore = teamScores.get(g.getTeam2());
//                teamScores.put(g.getTeam2(),previousScore + 2);
//                scoreBoard.setTeamScore(teamScores);
//
//            }
//
//        }

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
        if(i >= count) return false;
        else return true;
    }


    private static int simulateGame(Game g) {
        double fromConfig = 0.3;
        //Hey Mani, Get team Strength and use it accordingly.
        if(Math.random() <fromConfig){
            //Hey Mani, reverse the winning game here.
            return 1;
        }
        return 0;
    }

}
