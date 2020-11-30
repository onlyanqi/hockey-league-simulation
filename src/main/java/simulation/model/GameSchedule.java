package simulation.model;

import org.apache.log4j.Logger;
import simulation.serializers.ModelsForDeserialization.model.Game;
import simulation.state.HockeyContext;
import simulation.state.IHockeyContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameSchedule implements IGameSchedule {

    static Logger log = Logger.getLogger(GameSchedule.class);
    List<IGame> gameList = new ArrayList<>();
    int id;

    public GameSchedule() {
        setId(System.identityHashCode(this));
    }

    public GameSchedule(simulation.serializers.ModelsForDeserialization.model.GameSchedule gameSchedule) {
        IHockeyContext hockeyContextFactory = HockeyContext.getInstance();
        IModelFactory modelFactory = hockeyContextFactory.getModelFactory();
        this.id = gameSchedule.id;
        for (Game game : gameSchedule.gameList) {
            this.gameList.add(modelFactory.createGameFromDeserialization(game));
        }
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
        if (date == null) {
            log.error("provided date is null. Please make sure date is provided");
            throw new IllegalArgumentException("provided date is null. Please make sure date is provided");
        }
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
        if (gameList == null) {
            log.error("Game List is null");
            throw new IllegalArgumentException("Game List is null. Unable to get Played Games");
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
        if (gameList == null) {
            log.error("Game List is null");
            throw new IllegalArgumentException("Game List is null. Unable to get Games on given date");
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
