package simulation.factory;

import simulation.model.Injury;

public class InjuryConcrete {
    public Injury newInjury(){
        return new Injury();
    }
}
