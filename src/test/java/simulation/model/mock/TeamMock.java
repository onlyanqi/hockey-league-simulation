package simulation.model.mock;

import db.data.IPlayerFactory;
import db.data.ITeamFactory;
import simulation.model.Coach;
import simulation.model.Manager;
import simulation.model.Player;
import simulation.model.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamMock implements ITeamFactory {

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();
        IPlayerFactory playerFactory = new PlayerMock();
        addPlayerInList(playerList, playerFactory);
        return playerList;
    }

    private void addPlayerInList(List<Player> playerList, IPlayerFactory playerFactory) throws Exception {
        Player player;
        for (int i = 1; i < 21; i++) {
            player = new Player(i, playerFactory);
            playerList.add(player);
        }
    }

    @Override
    public int addTeam(Team team) throws Exception {
        team = new Team(1);
        return team.getId();
    }

    @Override
    public void loadTeamById(int id, Team team) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                //all correct data
                team.setName("Team1");
                team.setStartDate(new Date(2000, 0, 0));
                team.setEndDate(new Date(2050, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax1");
                team.setMascot("Tiger1");
                Manager Manager1 = new Manager();
                Manager1.setName("Manager1");
                team.setManager(Manager1);
                Coach Coach1 = new Coach();
                Coach1.setName("Coach1");
                Coach1.setSkating(0.8);
                Coach1.setShooting(0.5);
                Coach1.setChecking(0.3);
                Coach1.setSaving(0.5);
                team.setCoach(Coach1);
                team.setPlayerList(formPlayerList());
                break;

            case 2:
                //name null
                team.setName(null);
                team.setStartDate(new Date(2000, 0, 0));
                team.setEndDate(new Date(2050, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax2");
                team.setMascot("Tiger2");
                Manager Manager2 = new Manager();
                Manager2.setName("Manager2");
                team.setManager(Manager2);
                Coach Coach2 = new Coach();
                Coach2.setName("Rob");
                Coach2.setSkating(0.9);
                Coach2.setShooting(0.3);
                Coach2.setChecking(0.6);
                Coach2.setSaving(0.1);
                team.setCoach(Coach2);
                team.setPlayerList(formPlayerList());
                break;

            case 3:
                //end date less than start date
                team.setName("Invalid Date");
                team.setStartDate(new Date(2010, 0, 0));
                team.setEndDate(new Date(2000, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax3");
                team.setMascot("Tiger3");
                Manager Manager3 = new Manager();
                Manager3.setName("Manager3");
                team.setManager(Manager3);
                Coach Coach3 = new Coach();
                Coach3.setName("Coach3");
                Coach3.setSkating(0.2);
                Coach3.setShooting(0.5);
                Coach3.setChecking(0.4);
                Coach3.setSaving(0.8);
                team.setCoach(Coach3);
                team.setPlayerList(formPlayerList());
                break;

            case 4:
                //invalid position
                team.setName("Invalid Postion");
                team.setStartDate(new Date(2010, 0, 0));
                team.setEndDate(new Date(2000, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax4");
                team.setMascot("Tiger4");
                Manager Manager4 = new Manager();
                Manager4.setName("Manager4");
                team.setManager(Manager4);
                Coach Coach4 = new Coach();
                Coach4.setName("Coach3");
                Coach4.setSkating(0.2);
                Coach4.setShooting(0.5);
                Coach4.setChecking(0.4);
                Coach4.setSaving(0.8);
                team.setCoach(Coach4);
                team.setPlayerList(formPlayerList());
                break;
        }

    }


    @Override
    public void loadTeamByName(String teamName, Team team) throws Exception {
        team = new Team();
        team.setName("Team1");
        team.setStartDate(new Date(2000, 0, 0));
        team.setEndDate(new Date(2050, 0, 0));
        team.setDivisionId(1);
        team.setHometown("Halifax1");
        team.setMascot("Tiger1");
        Manager Manager1 = new Manager();
        Manager1.setName("Manager4");
        team.setManager(Manager1);
        Coach Coach1 = new Coach();
        Coach1.setName("Coach1");
        Coach1.setSkating(0.2);
        Coach1.setShooting(0.5);
        Coach1.setChecking(0.4);
        Coach1.setSaving(0.8);
        team.setCoach(Coach1);
        team.setPlayerList(formPlayerList());
    }

    @Override
    public List<Team> loadTeamListByDivisionId(int divisionId) throws Exception {
        DivisionMock loadDivisionMock = new DivisionMock();
        return loadDivisionMock.formTeamList();
    }

}
