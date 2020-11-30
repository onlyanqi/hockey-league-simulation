package simulation.model;

import persistance.dao.IManagerDao;

public class Manager extends SharedAttributes implements IManager {
    private int leagueId;
    private int teamId;
    private String personality;

    public Manager() {
        setId(System.identityHashCode(this));
    }

    public Manager(int id) {
        setId(id);
    }

    public Manager(IManager manager) {
        if (manager == null) {
            return;
        }
        this.setId(manager.getId());
        this.setLeagueId(manager.getLeagueId());
        this.setName(manager.getName());
        this.setTeamId(manager.getTeamId());
    }

    public Manager(persistance.serializers.ModelsForDeserialization.model.Manager manager) {
        this.leagueId = manager.leagueId;
        this.personality = manager.personality;
        this.teamId = manager.teamId;
        this.setId(manager.id);
        this.setName(manager.name);
    }

    public Manager(int id, IManagerDao managerFactory) throws Exception {
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
