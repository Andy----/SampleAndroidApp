package com.andrew.sampleandroidapp.models;

/**
 * Created by andrew on 19/11/2015.
 */
public class User {
    private int id;
    private String userName;
    private String password;

    public User() {

    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }


    public void setId() { this.id = id; }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) { this.password = password; }

    public int getId() { return id; }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
}
