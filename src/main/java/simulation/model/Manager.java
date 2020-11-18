package simulation.model;

import db.data.IManagerFactory;

public class Manager extends SharedAttributes {
    private int leagueId;
    private int teamId;
    private String personality;

    public Manager() {
    }

    public Manager(int id) {
        setId(id);
    }

    public Manager(Manager manager) {
        if (manager == null) {
            return;
        }
        this.setId(manager.getId());
        this.setLeagueId(manager.getLeagueId());
        if (isNotNull(manager.getName())) {
            this.setName(manager.getName());
        }
        this.setTeamId(manager.teamId);
    }

    public Manager(int id, IManagerFactory managerFactory) throws Exception {
        if (managerFactory == null) {
            return;
        }
        setId(id);
        managerFactory.loadManagerById(id, this);
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

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

}
