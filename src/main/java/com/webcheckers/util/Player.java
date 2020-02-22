package com.webcheckers.util;

public class Player {

    private boolean isLogged = true;
    public String name;

    public Player(String name){
        this.name = name;
    }

    public void logout(){
        this.isLogged = false;
    }

    public void login(){
        this.isLogged = true;
    }
}
