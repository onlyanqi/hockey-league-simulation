package model.pojo;

public class PointsTable {

    private int seasonStartYear;

    private int seasonEndYear;

    private int teamId;

    private int played;

    private int won;

    private int lost;

    private int tied;

    private int noResult;

    private int goalsForced;

    private int goalsAgainst;

    private double points;

    public int getSeasonStartYear() {
        return seasonStartYear;
    }

    public void setSeasonStartYear(int seasonStartYear) {
        this.seasonStartYear = seasonStartYear;
    }

    public int getSeasonEndYear() {
        return seasonEndYear;
    }

    public void setSeasonEndYear(int seasonEndYear) {
        this.seasonEndYear = seasonEndYear;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getTied() {
        return tied;
    }

    public void setTied(int tied) {
        this.tied = tied;
    }

    public int getNoResult() {
        return noResult;
    }

    public void setNoResult(int noResult) {
        this.noResult = noResult;
    }

    public int getGoalsForced() {
        return goalsForced;
    }

    public void setGoalsForced(int goalsForced) {
        this.goalsForced = goalsForced;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
