package simulation.model;

import java.time.LocalDate;

public class Game {

    private String team1;
    private String team2;
    private LocalDate date;
    private Boolean played;
    private Result winner;
    public enum Result{
        TEAM1,
        TEAM2,
        TIE
    };

    public Game() {
        this.played = false;
    }

    public Result getWinner() {
        return winner;
    }

    public void setWinner(Result winner) {
        this.winner = winner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Boolean getPlayed() {
        return played;
    }

    public void setPlayed(Boolean played) {
        this.played = played;
    }

    public Boolean isGameUnPlayed(){
        if(played){
            return false;
        }else{
            return true;
        }
    }
}
