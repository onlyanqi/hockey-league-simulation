package model.pojo;

public class Match {

    private int id;

    private int scheduleId;

    private int teamId1Score;

    private int teamId2Score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getTeamId1Score() {
        return teamId1Score;
    }

    public void setTeamId1Score(int teamId1Score) {
        this.teamId1Score = teamId1Score;
    }

    public int getTeamId2Score() {
        return teamId2Score;
    }

    public void setTeamId2Score(int teamId2Score) {
        this.teamId2Score = teamId2Score;
    }
}
