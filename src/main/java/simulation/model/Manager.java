package simulation.model;

public class Manager extends ParentObj{
    public Manager() {
    }

    public Manager(int id) {
        setId(id);
    }

//    public Manager(int id, IManagerFactory factory) throws Exception {
//        setId(id);
//        factory.loadManagerByLeagueId(id, this);
//    }

    public Manager(Manager manager){
        this.setId(manager.getId());
        this.setLeagueId(manager.getLeagueId());
        if(manager.getName()!=null){
            this.setName(manager.getName());
        }
        this.setTeamId(manager.teamId);
        if(manager.getEndDate()!=null){
            this.setEndDate(manager.getEndDate());
        }
        if(manager.getStartDate()!=null){
            this.setStartDate(manager.getStartDate());
        }
    }

    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int leagueId;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }
    private int teamId;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
