package factory;

import dao.AddPlayerDao;
import dao.LoadPlayerDao;
import simulation.data.IAddPlayerFactory;
import simulation.data.ILoadPlayerFactory;
import simulation.model.Player;

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
