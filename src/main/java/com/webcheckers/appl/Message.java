package com.webcheckers.appl



class Message {

    public enum Type = { INFO, ERROR }
    public String test;
    public Type type;

    Message(String test, Tyte type) {
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
