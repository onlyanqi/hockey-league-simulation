package simulation.model;

public class Coach extends ParentObj {
    public Coach() {
    }

    public Coach(int id) {
        setId(id);
    }

//    public Coach(int id, ICoachFactory factory) throws Exception {
//        setId(id);
//        factory.loadCoachByLeagueId((id, this);
//    }

    private String name;

    private int teamId;

    private int freeAgentId;

    private Double skating;

    private Double shooting;

    private Double checking;

    private Double saving;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getFreeAgentId() {
        return freeAgentId;
    }

    public void setFreeAgentId(int freeAgentId) {
        this.freeAgentId = freeAgentId;
    }

    public Double getSkating() {
        return skating;
    }

    public void setSkating(Double skating) {
        this.skating = skating;
    }

    public Double getShooting() {
        return shooting;
    }

    public void setShooting(Double shooting) {
        this.shooting = shooting;
    }

    public Double getChecking() {
        return checking;
    }

    public void setChecking(Double checking) {
        this.checking = checking;
    }

    public Double getSaving() {
        return saving;
    }

    public void setSaving(Double saving) {
        this.saving = saving;
    }


}
