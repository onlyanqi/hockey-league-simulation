package simulation.model.mock;

import db.data.IPlayerFactory;
import simulation.model.Player;

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

        switch (id) {
            case 1:
                //all correct data
                player.setName("Player1");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(true);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(27);
                player.setSkating(15);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);
                break;

            case 2:
                player.setName("Player2");
                player.setPosition(Player.Position.valueOf("defense"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(27);
                player.setSkating(15);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);
                break;

            case 3:
                player.setName("Player3");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(25);
                player.setSkating(20);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);

            case 4:
                player.setName("Player4");
                player.setPosition(Player.Position.valueOf("goalie"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(23);
                player.setSkating(10);
                player.setShooting(9);
                player.setChecking(15);
                player.setSaving(11);

            case 5:
                //forward with saving set not 0
                player.setName("Player5");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(27);
                player.setSkating(15);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);
                break;

            case 6:
                player.setName("Player6");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(27);
                player.setSkating(15);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);
                break;

            case 7:
                player.setName("Player7");
                player.setPosition(Player.Position.valueOf("defense"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(29);
                player.setSkating(15);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);
                break;

            case 8:
                player.setName("Player8");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(22);
                player.setSkating(15);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);
                break;

            case 9:
                player.setName("Player9");
                player.setPosition(Player.Position.valueOf("defense"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(22);
                player.setSkating(15);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);
                break;

            case 10:
                player.setName("Player10");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(33);
                player.setSkating(16);
                player.setShooting(21);
                player.setChecking(9);
                player.setSaving(0);
                break;

            case 11:
                player.setName("Player11");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(23);
                player.setSkating(9);
                player.setShooting(11);
                player.setChecking(29);
                player.setSaving(0);
                break;

            case 12:
                player.setName("Player12");
                player.setPosition(Player.Position.valueOf("goalie"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(25);
                player.setSkating(3);
                player.setShooting(19);
                player.setChecking(17);
                player.setSaving(5);
                break;

            case 13:
                player.setName("Player13");
                player.setPosition(Player.Position.valueOf("goalie"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(25);
                player.setSkating(3);
                player.setShooting(19);
                player.setChecking(17);
                player.setSaving(5);
                break;

            case 14:
                player.setName("Player14");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(25);
                player.setSkating(3);
                player.setShooting(19);
                player.setChecking(17);
                player.setSaving(0);
                break;

            case 15:
                player.setName("Player15");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(35);
                player.setSkating(13);
                player.setShooting(9);
                player.setChecking(27);
                player.setSaving(0);
                break;

            case 16:
                player.setName("Player16");
                player.setPosition(Player.Position.valueOf("defense"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(21);
                player.setSkating(15);
                player.setShooting(12);
                player.setChecking(22);
                player.setSaving(0);
                break;

            case 17:
                player.setName("Player17");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(28);
                player.setSkating(25);
                player.setShooting(11);
                player.setChecking(19);
                player.setSaving(0);
                break;

            case 18:
                player.setName("Player18");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(26);
                player.setSkating(16);
                player.setShooting(17);
                player.setChecking(18);
                player.setSaving(0);
                break;

            case 19:
                player.setName("Player19");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(19);
                player.setSkating(15);
                player.setShooting(21);
                player.setChecking(16);
                player.setSaving(0);
                break;

            case 20:
                player.setName("Player20");
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(36);
                player.setSkating(17);
                player.setShooting(16);
                player.setChecking(21);
                player.setSaving(0);
                break;

            case 21:
                //name is null
                player.setName(null);
                player.setPosition(Player.Position.valueOf("forward"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(36);
                player.setSkating(17);
                player.setShooting(16);
                player.setChecking(21);
                player.setSaving(0);
                break;

            case 22:
                //end date less than start date
                player.setName("Invalid Date");
                player.setStartDate(new Date(2010, 0, 0));
                player.setEndDate(new Date(2000, 0, 0));
                player.setAge(15);
                player.setHometown("Halifax");
                player.setPosition(Player.Position.valueOf("goalie"));
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setSeasonId(1);
                player.setCaptain(true);
                break;

            case 23:
                //invalid position
                player.setName("Invalid Position");
                player.setPosition(Player.Position.valueOf("referee"));
                player.setCaptain(false);
                player.setTeamId(1);
                player.setFreeAgentId(1);
                player.setAge(27);
                player.setSkating(15);
                player.setShooting(18);
                player.setChecking(12);
                player.setSaving(0);
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
