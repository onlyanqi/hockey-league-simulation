package model;

import data.ILoadUserFactory;

public class User extends ParentObj{

    public User(){}

    public User(int id){
        setId(id);
    }

    public User(int id, ILoadUserFactory factory) throws Exception {
        setId(id);
        factory.loadUserById(id, this);
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
