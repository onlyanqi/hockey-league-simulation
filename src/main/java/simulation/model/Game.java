package simulation.model;

import persistance.dao.IGameDao;

import java.time.LocalDate;

public class Game implements IGame {

    private int id;
    private String team1;
    private String team2;
    private LocalDate date;
    private Boolean played;
    private Result winner;

    public Game() {
        this.played = false;
        setId(System.identityHashCode(this));
    }

    public Game(int id, IGameDao factory) throws Exception {
        setId(id);
        factory.loadGameById(id, this);
    }

    public Game(persistance.serializers.ModelsForDeserialization.model.Game game) {
        this.id = game.id;
        this.date = game.date;
        this.played = game.played;
        this.team1 = game.team1;
        this.team2 = game.team2;
        this.winner = game.winner;
    }

    public static Result fromString(String text) {
        for (Result b : Result.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean isGameUnPlayed() {
        return !this.played;
    }

}
