package db.data;

import simulation.model.Injury;

public interface IInjuryFactory {
    int addInjury(Injury injury) throws Exception;

    Injury loadInjuryByLeagueId(int leagueId) throws Exception;

    void loadInjuryById(int id, Injury injury) throws Exception;
}
