package simulation.model;

import java.time.LocalDate;

public interface IGame {

    int getId();

    void setId(int id);

    Result getWinner();

    void setWinner(Result winner);

    LocalDate getDate();

    void setDate(LocalDate date);

    String getTeam1();

    void setTeam1(String team1);

    String getTeam2();

    void setTeam2(String team2);

    Boolean getPlayed();

    void setPlayed(Boolean played);

    Boolean isGameUnPlayed();

}
