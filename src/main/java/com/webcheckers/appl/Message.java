package com.webcheckers.appl;

public class Message {
    public enum Type { INFO, ERROR }
    public String test;
    public Type type;

    public Message(String test, Type type) {
        this.test = test;
        this.type = type;
    }

    public String getTest() {
        return this.test;
    }

    public Type getType() {
        return this.type;
    }

}
