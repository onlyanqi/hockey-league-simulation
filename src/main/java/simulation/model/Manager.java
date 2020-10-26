package simulation.model;

public class Manager extends ParentObj{
    String name;

    public Manager() {
    }

    public Manager(int id) {
        setId(id);
    }

    public Manager(Manager manager){
        this.setId(manager.getId());
        this.setLeagueId(manager.getLeagueId());
        if(manager.getName()!=null){
            this.setName(manager.getName());
        }
        this.setTeamId(manager.teamId);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private transient int leagueId;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }
    private transient int teamId;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
