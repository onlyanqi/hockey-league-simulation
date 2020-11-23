package db.data;

import simulation.model.IInjury;
import simulation.model.Injury;

public interface IInjuryDao {

    int addInjury(IInjury injury) throws Exception;

    IInjury loadInjuryByLeagueId(int leagueId) throws Exception;

    void loadInjuryById(int id, IInjury injury) throws Exception;
}
