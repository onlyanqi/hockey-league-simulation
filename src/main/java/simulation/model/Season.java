package simulation.model;

import db.data.ISeasonFactory;

public class Season extends SharedAttributes {

    public Season() {
    }

    public Season(int id) {
        setId(id);
    }

    public Season(int id, ISeasonFactory factory) throws Exception {
        if (factory == null) {
            return;
        }
        setId(id);
        factory.loadSeasonById(id, this);
    }

    public void addSeason(ISeasonFactory addSeasonFactory) throws Exception {
        if (addSeasonFactory == null) {
            return;
        }
        addSeasonFactory.addSeason(this);
    }
}
