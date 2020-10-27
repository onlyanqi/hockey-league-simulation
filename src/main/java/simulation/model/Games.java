package simulation.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Games {
    List<Game> gameList = new ArrayList<>();

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public List<Game> getGamesOnDate(LocalDate date) {
        List<Game> gameListOnGivenDate = new ArrayList<>();
        for (Game game : gameList) {
            if (game.date.equals(date)) {
                gameListOnGivenDate.add(game);
            }
        }
        return gameListOnGivenDate;
    }
}
