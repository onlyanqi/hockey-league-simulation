package simulation.dao;

import simulation.model.IInjury;

public class InjuryDao extends DBExceptionLog implements IInjuryDao {

    @Override
    public int addInjury(IInjury injury) throws Exception {
        return 0;
    }

    @Override
    public IInjury loadInjuryByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public void loadInjuryById(int id, IInjury injury) throws Exception {

    }
}
