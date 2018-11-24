package com.smartexsev.smartexserv.classes;

public class Message {
    private String text;
    private int id;

    public Message(String text, int id){
        this.text = text;
        this.id   = id;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
