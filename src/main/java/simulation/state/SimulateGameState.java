package simulation.state;

import simulation.model.Game;
import simulation.model.League;
import simulation.model.RegularSeasonScoreBoard;
import simulation.model.Team;

import java.util.HashMap;
import java.util.List;
import java.lang.Math;

import static java.lang.Math.random;

public class SimulateGameState implements ISimulateState {

    private HockeyContext hockeyContext;
    private League league;

    public SimulateGameState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        List<Game> gamesOnCurrentDay = league.getGames().getGamesOnDate(league.getCurrentDate());
        Game game = gamesOnCurrentDay.get(0);

        int winner = simulateGame(game);
        game.setResult(winner);
        RegularSeasonScoreBoard regularSeasonScoreBoard = league.getRegularSeasonScoreBoard();
        HashMap<String,Integer> teamScores = regularSeasonScoreBoard.getTeamsScore();

        if(winner ==0){
            regularSeasonScoreBoard.setTeamScore(game.getTeam1());
        }else{
            regularSeasonScoreBoard.setTeamScore(game.getTeam2());
        }

        return exit();
    }

    private ISimulateState exit() {
        return new InjuryCheckState(hockeyContext);
    }

    private int simulateGame(Game game) {
        //Hey Mani, 0 is team1 and 1 is team2. enum this later.
        int winner;
        double upset = league.getGamePlayConfig().getGameResolver().getRandomWinChance();
        Team team1 = league.getTeamByTeamName(game.getTeam1());
        Team team2 = league.getTeamByTeamName(game.getTeam2());


        if(team1!=null && team2!=null){
            if(team1.getStrength()>team2.getStrength()){
                winner = 0;
            }else{
                winner = 1;
            }
            if(Math.random() <= upset){
                if(winner ==0){
                    winner = 1;
                }else{
                    winner =0;
                }
            }
            return winner;
        }
        return 0;
    }
}
