package com.smartexsev.smartexserv.classes;

public class User {
    private String name;
    private int id;
    private String password;

    public User (String name, int id, String password) {
        this.name     = name;
        this.id       = id;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
}
