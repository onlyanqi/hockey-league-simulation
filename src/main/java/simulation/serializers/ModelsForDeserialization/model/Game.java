package simulation.serializers.ModelsForDeserialization.model;

import simulation.model.Result;

import java.time.LocalDate;

public class Game {

    public int id;
    public String team1;
    public String team2;
    public LocalDate date;
    public Boolean played;
    public Result winner;
}
