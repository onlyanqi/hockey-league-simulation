package model;

import data.IFreeAgentFactory;

import java.util.List;

public class FreeAgent extends ParentObj{

    public FreeAgent(){}

    public FreeAgent(long id){
        setId(id);
    }

    public FreeAgent(long id, IFreeAgentFactory factory){
        setId(id);
        factory.loadFreeAgent(id, this);
    }

    private List<Player> playerList;

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

}
