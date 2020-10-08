package model;

import data.ILoadUserFactory;

public class LoadUserMock implements ILoadUserFactory {

    @Override
    public void loadUserById(int id, User user){

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
    public User loadUserByName(String userName) throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("User1");
        user.setPassword("Password1");
        return user;
    }

}
