package simulation.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface IGameSchedule {

    int getId();

    void setId(int id);

    List<IGame> getGameList();

    void setGameList(List<IGame> gameList);

    List<IGame> getUnPlayedGamesOnDate(LocalDate date);

    IGame getLastPlayedGame();

    List<IGame> getGamesOnDate(LocalDate date);

    Boolean doGamesDoesNotExistOnOrAfterDate(LocalDate date);

    Boolean doGamesDoesNotExistAfterDate(LocalDate date);
}
