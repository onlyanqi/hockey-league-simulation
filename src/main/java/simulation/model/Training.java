package simulation.model;

import db.data.ITrainingFactory;

public class Training {


    private int id;
    private int daysUntilStatIncreaseCheck;
    private int leagueId;

    public Training() {
        setId(System.identityHashCode(this));
    }

    public Training(int LeagueId, ITrainingFactory trainingFactory) throws Exception {
        if (trainingFactory == null) {
            return;
        }
        setId(id);
        trainingFactory.loadTrainingByLeagueId(id, this);
    }

    public int getDaysUntilStatIncreaseCheck() {
        return daysUntilStatIncreaseCheck;
    }

    public void setDaysUntilStatIncreaseCheck(int daysUntilStatIncreaseCheck) {
        this.daysUntilStatIncreaseCheck = daysUntilStatIncreaseCheck;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
