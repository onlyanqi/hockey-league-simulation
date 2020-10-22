package simulation.model;

import java.util.ArrayList;
import java.util.List;

public  class Games {
    List<Game> gameList = new ArrayList<>();

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }
}
