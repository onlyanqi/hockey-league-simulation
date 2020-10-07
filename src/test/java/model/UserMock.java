package model;

import data.IUserFactory;

public class UserMock implements IUserFactory {

    @Override
    public void loadUserByName(int id, User user){

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

    @Override
    public long addUser(User user) throws Exception {
        return 0;
    }

}
