package model;

import data.IPlayerFactory;
import data.ITeamFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamMock implements ITeamFactory {

    private List formPlayerList(){
        List<Player> playerList = new ArrayList<>();

        IPlayerFactory playerFactory = new PlayerMock();
        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(5, playerFactory);
        playerList.add(player);

        return playerList;
    }

    @Override
    public void loadTeam(long id, Team team){

        switch (new Long(id).intValue()){
            case 1:
                //all correct data
                team.setName("Team1");
                team.setStartDate(new Date(2000, 0, 0));
                team.setEndDate(new Date(2050, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax");
                team.setMascot("Tiger");
                team.setPlayerList(formPlayerList());
                break;

            case 2:
                //name null
                team.setName(null);
                team.setStartDate(new Date(2000, 0, 0));
                team.setEndDate(new Date(2050, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax");
                team.setMascot("Tiger");
                team.setPlayerList(formPlayerList());
                break;

            case 3:
                //end date less than start date
                team.setName("Invalid Date");
                team.setStartDate(new Date(2010, 0, 0));
                team.setEndDate(new Date(2000, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax");
                team.setMascot("Tiger");
                team.setPlayerList(formPlayerList());
                break;

            case 4:
                //invalid role
                team.setName("Invalid Role");
                team.setStartDate(new Date(2010, 0, 0));
                team.setEndDate(new Date(2000, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax");
                team.setMascot("Tiger");
                team.setPlayerList(formPlayerList());
                break;
        }

    }

}
