package simulation.model;

import db.data.IPlayerFactory;
import db.data.ITeamFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamMock implements ITeamFactory {

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        IPlayerFactory playerFactory = new PlayerMock();
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

    @Override
    public void loadTeamById(int id, Team team) throws Exception {

        switch (new Long(id).intValue()){
            case 1:
                //all correct data
                team.setName("Team1");
                team.setStartDate(new Date(2000, 0, 0));
                team.setEndDate(new Date(2050, 0, 0));
                team.setDivisionId(1);
                team.setHometown("Halifax1");
                team.setMascot("Tiger1");
                team.setGeneralManager("Manager1");
                team.setHeadCoach("Coach1");
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
                team.setGeneralManager("Manager2");
                team.setHeadCoach("Coach2");
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
                team.setGeneralManager("Manager3");
                team.setHeadCoach("Coach3");
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
                team.setGeneralManager("Manager4");
                team.setHeadCoach("Coach4");
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
        team.setGeneralManager("Manager1");
        team.setHeadCoach("Coach1");
        team.setPlayerList(formPlayerList());
    }

    @Override
    public List<Team> loadTeamListByDivisionId(int divisionId) throws Exception {
        DivisionMock loadDivisionMock = new DivisionMock();
        return loadDivisionMock.formTeamList();
    }

}
