package simulation.model;

import userIO.ConsoleOutput;

public class Coach extends SharedAttributes {
    public Coach() {
    }

    public Coach(int id) {
        setId(id);
    }

//    public Coach(int id, ICoachFactory factory) throws Exception {
//        setId(id);
//        factory.loadCoachByLeagueId((id, this);
//    }

    public Coach(Coach coach){
        this.setId(coach.getId());
        if (coach.getName() != null) {
            this.setName(coach.getName());
        }
        this.setTeamId(coach.getTeamId());
        this.setChecking(coach.getChecking());
        this.setLeagueId(coach.getLeagueId());
        this.setSaving(coach.getSaving());
        this.setShooting(coach.getShooting());
        this.setSkating(coach.getSkating());
        if(coach.getEndDate()!=null){
            this.setEndDate(coach.getEndDate());
        }
        if(coach.getStartDate()!=null){
            this.setStartDate(coach.getStartDate());
        }
    }
    private String name;

    private int teamId;

    private int leagueId;

    private Double skating;

    private Double shooting;

    private Double checking;

    private Double saving;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }


    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
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

    public void printCoach(int i){
        ConsoleOutput.printToConsole("Coach id: "+(i));
        ConsoleOutput.printToConsole("\t Coach name: "+this.getName());
        ConsoleOutput.printToConsole("\t Skating strength: "+this.getSkating());
        ConsoleOutput.printToConsole("\t Shooting strength: "+this.getShooting());
        ConsoleOutput.printToConsole("\t Checking strength: "+this.getChecking());
        ConsoleOutput.printToConsole("\t Saving strength: "+this.getSaving());
        ConsoleOutput.printToConsole("\n");
    }


}
