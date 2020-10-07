package model;

import data.ISeasonFactory;

public class Season extends ParentObj{

    public Season(){}

    public Season(int id){
        setId(id);
    }

    public Season(int id, ISeasonFactory factory) throws Exception {
        setId(id);
        factory.loadSeasonByName(id, this);
    }

}
