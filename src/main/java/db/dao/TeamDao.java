package db.dao;

import db.data.ITeamDao;
import simulation.model.ITeam;

import java.util.List;

public class TeamDao extends DBExceptionLog implements ITeamDao {


    @Override
    public int addTeam(ITeam team) throws Exception {
        return 0;
    }

    @Override
    public void loadTeamById(int id, ITeam team) throws Exception {

    }

    @Override
    public void loadTeamByName(String teamName, ITeam team) throws Exception {

    }

    @Override
    public List<ITeam> loadTeamListByDivisionId(int divisionId) throws Exception {
        return null;
    }

    @Override
    public void updateTeamById(ITeam team) throws Exception {

    }
}
