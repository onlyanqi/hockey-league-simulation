package simulation.mock;

import db.data.IPlayerDao;
import db.data.ITeamDao;
import simulation.model.*;

import java.util.ArrayList;
import java.util.List;

public class TeamMock implements ITeamDao {

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();
        IPlayerDao playerFactory = new PlayerMock();
        addPlayerInList(playerList, playerFactory);
        return playerList;
    }

    private void addPlayerInList(List<Player> playerList, IPlayerDao playerFactory) throws Exception {
        Player player;
        for (int i = 1; i < 31; i++) {
            player = new Player(i, playerFactory);
            playerList.add(player);
        }
    }

    @Override
    public int addTeam(ITeam team) {
        team = new Team(1);
        return team.getId();
    }

    @Override
    public void loadTeamById(int id, ITeam team) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                team.setName("Team1");
                team.setDivisionId(1);
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
                team.setLossPoint(0);
                team.setStrength();
                team.setAiTeam(true);
                team.setTradeOfferCountOfSeason(0);
                team.setActivePlayerList();
                break;

            case 2:
                team.setName(null);
                team.setDivisionId(1);
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
                team.setLossPoint(2);
                team.setPlayerList(formPlayerList());
                team.setStrength();
                team.setAiTeam(true);
                team.setTradeOfferCountOfSeason(2);
                break;

            case 3:
                team.setName("Invalid Date");
                team.setDivisionId(1);
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
                team.setStrength();
                team.setAiTeam(true);
                team.setTradeOfferCountOfSeason(0);
                team.setActivePlayerList();
                break;

            case 4:
                team.setName("Invalid Postion");
                team.setDivisionId(1);
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
                team.setStrength();
                team.setAiTeam(false);
                team.setTradeOfferCountOfSeason(1);
                team.setActivePlayerList();
                break;

            case 5:
                team.setName("TeamName");
                team.setDivisionId(1);
                team.setMascot("Tiger5");
                Manager Manager5 = new Manager();
                Manager5.setName("Manager5");
                team.setManager(Manager5);
                Coach Coach5 = new Coach();
                Coach5.setName("Coach3");
                Coach5.setSkating(0.2);
                Coach5.setShooting(0.5);
                Coach5.setChecking(0.4);
                Coach5.setSaving(0.8);
                team.setCoach(Coach5);
                team.setLossPoint(4);
                team.setPlayerList(formPlayerList());
                team.setStrength();
                team.setAiTeam(true);
                team.setTradeOfferCountOfSeason(6);
                break;
        }

    }


    @Override
    public void loadTeamByName(String teamName, ITeam team) throws Exception {
        team = new Team();
        team.setName("Team1");
        team.setDivisionId(1);
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
        team.setActivePlayerList();
    }

    @Override
    public List<ITeam> loadTeamListByDivisionId(int divisionId) throws Exception {
        DivisionMock loadDivisionMock = new DivisionMock();
        return loadDivisionMock.formTeamList();
    }

    @Override
    public void updateTeamById(ITeam team) throws Exception {
        team.setName("Team1");
        team.setDivisionId(1);
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
        team.setLossPoint(0);
        team.setStrength();
        team.setAiTeam(true);
        team.setTradeOfferCountOfSeason(0);
    }

}
