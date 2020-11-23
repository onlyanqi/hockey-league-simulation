package simulation.factory;

import simulation.model.IInjury;
import simulation.model.Injury;

public class InjuryConcrete implements IInjuryFactory{

    public IInjury newInjury() {
        return new Injury();
    }

}
