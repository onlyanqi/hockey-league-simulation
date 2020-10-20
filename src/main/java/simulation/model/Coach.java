package simulation.model;

import java.util.List;

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

    private float skating;

    private float shooting;

    private float checking;

    private float saving;

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

    public float getSkating() {
        return skating;
    }

    public void setSkating(float skating) {
        this.skating = skating;
    }

    public float getShooting() {
        return shooting;
    }

    public void setShooting(float shooting) {
        this.shooting = shooting;
    }

    public float getChecking() {
        return checking;
    }

    public void setChecking(float checking) {
        this.checking = checking;
    }

    public float getSaving() {
        return saving;
    }

    public void setSaving(float saving) {
        this.saving = saving;
    }


}
