package simulation.model;

import simulation.dao.ISeasonDao;

public class Season extends SharedAttributes implements ISeason {

    public Season() {
        setId(System.identityHashCode(this));
    }

    public Season(int id) {
        setId(id);
    }

    public Season(int id, ISeasonDao factory) throws Exception {
        setId(id);
        factory.loadSeasonById(id, this);
    }

    public void addSeason(ISeasonDao addSeasonFactory) throws Exception {
        if (addSeasonFactory == null) {
            return;
        }
        addSeasonFactory.addSeason(this);
    }
}
