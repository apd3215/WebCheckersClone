package com.webcheckers.appl;

public class Player {

    private boolean isLogged = true;
    private String name;

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void logout(){
        this.isLogged = false;
    }

    public void login(){
        this.isLogged = true;
    }
}
