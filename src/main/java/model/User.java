package model;

import data.IUserFactory;

public class User extends ParentObj{

    public User(){}

    public User(int id){
        setId(id);
    }

    public User(int id, IUserFactory factory) throws Exception {
        setId(id);
        factory.loadUserByName(id, this);
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
