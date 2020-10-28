package simulation.state;

import simulation.model.Game;
import simulation.model.League;
import simulation.model.Team;
import simulation.model.TeamStanding;

import java.util.List;
import java.lang.Math;

public class SimulateGameState implements ISimulateState {

    private HockeyContext hockeyContext;
    private League league;

    public SimulateGameState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        List<Game> gamesOnCurrentDay = league.getGames().getUnplayedGamesOnDate(league.getCurrentDate());
        Game game = gamesOnCurrentDay.get(0);

        simulateGame(game);
        updateTeamStandings(game);

        return exit();
    }

    private ISimulateState exit() {
        return new InjuryCheckState(hockeyContext);
    }

    private void simulateGame(Game game) {
        double upset = league.getGamePlayConfig().getGameResolver().getRandomWinChance();
        Team team1 = league.getTeamByTeamName(game.getTeam1());
        Team team2 = league.getTeamByTeamName(game.getTeam2());

        if(team1!=null && team2!=null){
            if(team1.getStrength()>team2.getStrength()){
                game.setWinner(Game.Result.TEAM1);
            }else{
                game.setWinner(Game.Result.TEAM2);
            }
            if(Math.random() <= upset){
                if(game.getWinner().equals(Game.Result.TEAM1)){
                    game.setWinner(Game.Result.TEAM2);
                }else{
                    game.setWinner(Game.Result.TEAM1);
                }
            }
            game.setPlayed(true);
        }
    }

    private void updateTeamStandings(Game game){
        TeamStanding teamStanding = league.getActiveTeamStanding();

        if(game.getWinner().equals(Game.Result.TEAM1)){
            teamStanding.setTeamPoints(game.getTeam1());
            teamStanding.setTeamWins(game.getTeam1());
            teamStanding.setTeamLoss(game.getTeam2());
        }else if(game.getWinner().equals(Game.Result.TEAM2)){
            teamStanding.setTeamPoints(game.getTeam2());
            teamStanding.setTeamWins(game.getTeam1());
            teamStanding.setTeamLoss(game.getTeam2());
        }
    }
}
