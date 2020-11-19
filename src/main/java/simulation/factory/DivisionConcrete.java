package simulation.factory;

import db.data.IDivisionFactory;
import simulation.model.Division;

public class DivisionConcrete {

    public Division newDivision() {
        return new Division();
    }


}
