package simulation.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public  class Games {
    List<Game> gameList = new ArrayList<>();

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }
    public List<Game> getUnplayedGamesOnDate(LocalDate date){
        List<Game> gameListOnGivenDate = new ArrayList<>();
        for(Game game: gameList){
            if(game.getDate().equals(date) && game.isGameUnPlayed()){
                gameListOnGivenDate.add(game);
            }
        }
        return  gameListOnGivenDate;
    }
    public Boolean doGamesDoesNotExistAfterDate(LocalDate date){
        for(Game game: gameList){
            if(game.getDate().isAfter(date)){
                return false;
            }
        }
        return true;
    }
}
