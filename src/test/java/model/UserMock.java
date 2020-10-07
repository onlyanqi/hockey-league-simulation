package model;

import data.IPlayerFactory;
import data.IUserFactory;

import java.util.Date;

public class UserMock implements IUserFactory {

    @Override
    public void loadUser(long id, User user){

        switch (new Long(id).intValue()){
            case 1:
                //all correct data
                user.setId(1);
                user.setName("User1");
                user.setPassword("Password1");
                break;

            case 2:
                //name null
                user.setId(2);
                user.setName(null);
                user.setPassword("Password2");
                break;

            case 3:
                //password null
                user.setId(3);
                user.setName("Player3");
                user.setPassword(null);
                break;
        }

    }

}
