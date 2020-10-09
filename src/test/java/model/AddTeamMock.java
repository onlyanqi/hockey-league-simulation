package model;

import data.IAddTeamFactory;
import data.ILoadPlayerFactory;
import data.ILoadTeamFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTeamMock implements IAddTeamFactory {

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        ILoadPlayerFactory playerFactory = new LoadPlayerMock();
        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(5, playerFactory);
        playerList.add(player);

        return playerList;
    }

    @Override
    public int addTeam(Team team) throws Exception {
        team = new Team(1);
        return team.getId();
    }
}
