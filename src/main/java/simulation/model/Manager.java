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
