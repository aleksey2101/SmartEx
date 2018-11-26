package com.univerhelper.smartex.univerhelper.Classes;

public class User {
    private String name;
    private int id;
    private String password;
    private int group;

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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
