package persistance.dao;

import simulation.model.IInjury;

public interface IInjuryDao {

    int addInjury(IInjury injury) throws Exception;

    IInjury loadInjuryByLeagueId(int leagueId) throws Exception;

    void loadInjuryById(int id, IInjury injury) throws Exception;
}
