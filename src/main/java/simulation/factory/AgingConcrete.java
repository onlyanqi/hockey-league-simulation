package simulation.factory;

import simulation.model.Aging;

public class AgingConcrete {
    public Aging newAging(){
        return new Aging();
    }
}
