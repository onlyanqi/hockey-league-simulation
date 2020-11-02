package simulation.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Games {

    public Games() {
    }

    List<Game> gameList = new ArrayList<>();
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public List<Game> getUnPlayedGamesOnDate(LocalDate date) {
        List<Game> gameListOnGivenDate = new ArrayList<>();
        for (Game game : gameList) {
            if (game.getDate().equals(date) && game.isGameUnPlayed()) {
                gameListOnGivenDate.add(game);
            }
        }
        return gameListOnGivenDate;
    }

    public Game getLastPlayedGame() {
        for (int i = 0; i < gameList.size(); i++) {
            if (gameList.get(i).isGameUnPlayed()) {
                return gameList.get(i - 1);
            }
        }
        return gameList.get(gameList.size() - 1);
    }

    public List<Game> getGamesOnDate(LocalDate date) {
        List<Game> gameListOnGivenDate = new ArrayList<>();
        for (Game game : gameList) {
            if (game.getDate().equals(date)) {
                gameListOnGivenDate.add(game);
            }
        }
        return gameListOnGivenDate;
    }

    public Boolean doGamesDoesNotExistOnOrAfterDate(LocalDate date) {
        for (Game game : gameList) {
            if (game.getDate().compareTo(date) >= 0) {
                return false;
            }
        }
        return true;
    }

    public Boolean doGamesDoesNotExistAfterDate(LocalDate date) {
        for (Game game : gameList) {
            if (game.getDate().compareTo(date) > 0) {
                return false;
            }
        }
        return true;
    }
}
