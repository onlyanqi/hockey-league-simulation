package simulation.model;

import db.data.IManagerFactory;

public class Manager extends ParentObj {
public class Manager extends SharedAttributes {
    private int leagueId;
    private int teamId;

    public Manager() {
    }


    public Manager(int id) {
        setId(id);
    }

    public Manager(Manager manager) {
        this.setId(manager.getId());
        this.setLeagueId(manager.getLeagueId());
        if (manager.getName() != null) {
            this.setName(manager.getName());
        }
        this.setTeamId(manager.teamId);
    }

    public Manager(int id, IManagerFactory managerFactory) throws Exception{
        setId(id);
        managerFactory.loadManagerById(id,this);
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

}
