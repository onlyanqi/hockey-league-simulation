package simulation.model;

import simulation.data.IAddSeasonFactory;
import simulation.data.ILoadSeasonFactory;

public class Season extends ParentObj{

    public Season(){}

    public Season(int id){
        setId(id);
    }

    public Season(int id, ILoadSeasonFactory factory) throws Exception {
        setId(id);
        factory.loadSeasonById(id, this);
    }

    public void addSeason(IAddSeasonFactory addSeasonFactory) throws Exception {
        addSeasonFactory.addSeason(this);
    }

}
