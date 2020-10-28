package simulation.model;

import db.data.IPlayerFactory;

import java.util.Date;
import java.util.List;

public class PlayerMock implements IPlayerFactory {

    @Override
    public int addPlayer(Player player) throws Exception {
        player = new Player(1);
        return player.getId();
    }

    @Override
    public void loadPlayerById(int id, Player player) {

        switch (new Long(id).intValue()) {
            case 1:
                //all correct data
                player.setName("Player1");
                player.setAge(15);
                player.setHometown("Halifax");
                player.setPosition(Player.Position.valueOf("goalie"));
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setSeasonId(1);
                player.setCaptain(true);
                player.setSkating(10);
                player.setSaving(10);
                player.setStrength();
                break;

            case 2:
                //name null
                player.setName(null);
                player.setAge(15);
                player.setHometown("Halifax");
                player.setPosition(Player.Position.valueOf("goalie"));
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setSeasonId(1);
                player.setCaptain(false);
                player.setSkating(20);
                player.setSaving(20);
                player.setStrength();
                break;

            case 3:
                //end date less than start date
                player.setName("Invalid Date");
                player.setAge(15);
                player.setHometown("Halifax");
                player.setPosition(Player.Position.valueOf("goalie"));
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setSeasonId(1);
                player.setCaptain(true);
                player.setSkating(30);
                player.setSaving(30);
                player.setStrength();
                break;

            case 4:
                //invalid position
                player.setName("Invalid Position");
                player.setAge(15);
                player.setHometown("Halifax");
                player.setPosition(Player.Position.valueOf("referee"));
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setSeasonId(1);
                player.setCaptain(false);
                player.setSkating(40);
                player.setSaving(40);
                player.setStrength();
                break;

            case 5:
                //all correct data
                player.setName("Player5");
                player.setAge(17);
                player.setHometown("Montreal");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setSeasonId(1);
                player.setCaptain(true);
                player.setSkating(50);
                player.setSaving(50);
                player.setStrength();
                break;

            case 6:
                //all correct data
                player.setName("Player6");
                player.setAge(21);
                player.setPosition(Player.Position.valueOf("defense"));
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setSeasonId(1);
                player.setSkating(60);
                player.setSaving(60);
                player.setStrength();
                break;

            case 7:
                //all correct data
                player.setName("Player7");
                player.setAge(22);
                player.setPosition(Player.Position.valueOf("forward"));
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setSeasonId(1);
                player.setSkating(70);
                player.setSaving(70);
                player.setStrength();
                break;
        }

    }

    @Override
    public List<Player> loadPlayerListByFreeAgentId(int teamId) throws Exception {
        TeamMock loadTeamMock = new TeamMock();
        return loadTeamMock.formPlayerList();
    }

    @Override
    public List<Player> loadPlayerListByTeamId(int teamId) throws Exception {
        FreeAgentMock loadFreeAgentMock = new FreeAgentMock();
        return loadFreeAgentMock.formPlayerList();
    }

}
