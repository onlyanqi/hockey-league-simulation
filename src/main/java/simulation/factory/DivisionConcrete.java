package simulation.factory;

import simulation.model.Division;
import simulation.model.IDivision;

public class DivisionConcrete {

    public IDivision newDivision() {
        return new Division();
    }


}
