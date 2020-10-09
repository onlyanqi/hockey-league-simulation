package factory;

import dao.AddLeagueDao;
import dao.AddPlayerDao;
import dao.LoadLeagueDao;
import dao.LoadPlayerDao;
import data.IAddLeagueFactory;
import data.IAddPlayerFactory;
import data.ILoadLeagueFactory;
import data.ILoadPlayerFactory;
import model.League;
import model.Player;

public class PlayerConcrete {

    public Player newPlayer(){
        return new Player();
    }

    public ILoadPlayerFactory newLoadPlayerFactory(){
        return new LoadPlayerDao();
    }

    public IAddPlayerFactory newAddPlayerFactory(){
        return new AddPlayerDao();
    }

}
