package simulation.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameSchedule implements IGameSchedule{

    List<IGame> gameList = new ArrayList<>();
    int id;
    public GameSchedule() {
        setId(System.identityHashCode(this));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<IGame> getGameList() {
        return gameList;
    }

    public void setGameList(List<IGame> gameList) {
        this.gameList = gameList;
    }

    public List<IGame> getUnPlayedGamesOnDate(LocalDate date) {
        List<IGame> gameListOnGivenDate = new ArrayList<>();
        for (IGame game : gameList) {
            if (game.getDate().equals(date) && game.isGameUnPlayed()) {
                gameListOnGivenDate.add(game);
            }
        }
        return gameListOnGivenDate;
    }

    public IGame getLastPlayedGame() {
        for (int i = 0; i < gameList.size(); i++) {
            if (gameList.get(i).isGameUnPlayed()) {
                return gameList.get(i - 1);
            }
        }
        return gameList.get(gameList.size() - 1);
    }

    public List<IGame> getGamesOnDate(LocalDate date) {
        List<IGame> gameListOnGivenDate = new ArrayList<>();
        for (IGame game : gameList) {
            if (game.getDate().equals(date)) {
                gameListOnGivenDate.add(game);
            }
        }
        return gameListOnGivenDate;
    }

    public Boolean doGamesDoesNotExistOnOrAfterDate(LocalDate date) {
        for (IGame game : gameList) {
            if (game.getDate().compareTo(date) >= 0) {
                return false;
            }
        }
        return true;
    }

    public Boolean doGamesDoesNotExistAfterDate(LocalDate date) {
        for (IGame game : gameList) {
            if (game.getDate().compareTo(date) > 0) {
                return false;
            }
        }
        return true;
    }
}
