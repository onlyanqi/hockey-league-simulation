package simulation.model;

import db.data.ISeasonFactory;


public class Season extends ParentObj{

    String name;

    public Season(){}

    public Season(int id){
        setId(id);
    }

    public Season(int id, ISeasonFactory factory) throws Exception {
        setId(id);
        factory.loadSeasonById(id, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSeason(ISeasonFactory addSeasonFactory) throws Exception {
        addSeasonFactory.addSeason(this);
    }

}
