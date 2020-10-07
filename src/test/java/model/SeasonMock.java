package model;

import data.ISeasonFactory;
import data.IUserFactory;

public class SeasonMock implements ISeasonFactory {

    @Override
    public void loadSeason(long id, Season season){

        switch (new Long(id).intValue()){
            case 1:
                //all correct data
                season.setId(1);
                season.setName("Season1");
                break;

            case 2:
                //name null
                season.setId(2);
                season.setName(null);
                break;

        }

    }

}
