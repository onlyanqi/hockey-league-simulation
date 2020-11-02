package simulation.mock;

import db.data.IGameFactory;
import simulation.model.Game;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class GameMock implements IGameFactory {
    @Override
    public long addGame(int leagueId, Game game){
        game.setId(1);
        return game.getId();
    }

    @Override
    public void loadGameById(int id, Game game) {
        switch (id){
            case 0:
                game.setId(id);
                game.setWinner(Game.Result.TEAM1);
                game.setPlayed(true);
                game.setTeam1("Ottawa High Rollers");
                game.setTeam2("Atlanta Milk Cartons");
                game.setDate(LocalDate.of(2020, Month.OCTOBER, 29));
                break;
            case 1:
                game.setId(id);
                game.setWinner(Game.Result.TEAM2);
                game.setPlayed(true);
                game.setTeam1("Gander Milk Cartons");
                game.setTeam2("Shanghai Mutineers");
                game.setDate(LocalDate.of(2020, Month.OCTOBER, 30));
                break;
            case 2:
                game.setId(id);
                game.setWinner(null);
                game.setPlayed(false);
                game.setTeam1("Atlanta Wild Cats");
                game.setTeam2("Jakarta High Rollers");
                game.setDate(LocalDate.of(2020, Month.NOVEMBER, 02));
                break;
            case 3:
                game.setId(id);
                game.setWinner(null);
                game.setPlayed(false);
                game.setTeam1("Saint John Lazy Dogs");
                game.setTeam2("Atlanta Wild Cats");
                game.setDate(LocalDate.of(2020, Month.NOVEMBER, 03));
                break;
            case 4:
                game.setId(id);
                game.setWinner(null);
                game.setPlayed(false);
                game.setTeam1("team11");
                game.setTeam2("team12");
                game.setDate(LocalDate.of(2020, Month.NOVEMBER,03));
                break;
        }
    }

    @Override
    public List<Game> loadGamesByLeagueId(int leagueId) {
        List<Game> gameList = null;
        switch (leagueId) {
            case 0:
                Game game1 = new Game();
                game1.setId(4);
                game1.setWinner(null);
                game1.setPlayed(false);
                game1.setTeam1("Ottawa High Rollers");
                game1.setTeam2("Jakarta High Rollers");
                game1.setDate(LocalDate.of(2020, Month.NOVEMBER, 02));

                Game game2 = new Game();
                game1.setId(5);
                game1.setWinner(Game.Result.TEAM2);
                game1.setPlayed(true);
                game1.setTeam1("Ottawa High Rollers");
                game1.setTeam2("Atlanta Milk Cartons");
                game1.setDate(LocalDate.of(2020, Month.OCTOBER, 27));

                gameList.add(game1);
                gameList.add(game2);
                break;
            case 1:
                Game game3 = new Game();
                game3.setId(7);
                game3.setWinner(null);
                game3.setPlayed(false);
                game3.setTeam1("Wuhan Mutineers");
                game3.setTeam2("Seattle Farmers");
                game3.setDate(LocalDate.of(2020, Month.NOVEMBER, 01));

                Game game4 = new Game();
                game4.setId(8);
                game4.setWinner(Game.Result.TEAM1);
                game4.setPlayed(true);
                game4.setTeam1("Atlanta Wild Cats");
                game4.setTeam2("Moscow Lazy Dogs");
                game4.setDate(LocalDate.of(2020, Month.OCTOBER, 25));

                gameList.add(game3);
                gameList.add(game4);
                break;
        }
        return gameList;
    }

    @Override
    public void updateGameById(Game game) {
        game.setId(game.getId());
        game.setTeam1(game.getTeam1());
        game.setTeam2(game.getTeam2());
        game.setDate(game.getDate());
        game.setPlayed(game.getPlayed());
        game.setWinner(game.getWinner());
    }
}