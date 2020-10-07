package model;

import data.ISeasonFactory;

public class Season extends ParentObj{

    public Season(){}

    public Season(long id){
        setId(id);
    }

    public Season(long id, ISeasonFactory factory){
        setId(id);
        factory.loadSeason(id, this);
    }

}
