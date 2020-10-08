package model;

import data.ILoadSeasonFactory;

public class Season extends ParentObj{

    public Season(){}

    public Season(int id){
        setId(id);
    }

    public Season(int id, ILoadSeasonFactory factory) throws Exception {
        setId(id);
        factory.loadSeasonById(id, this);
    }

}
