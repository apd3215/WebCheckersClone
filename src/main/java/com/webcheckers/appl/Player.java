package com.webcheckers.appl;

/**
 * Player class, represents a player in webcheckers.
 */
public class Player {
    private boolean isPlaying = false;
    private boolean isLogged = true;
    private String name;

    /**
     * Constructor, create a new player.
     * @param name the name of the player
     */
    public Player(String name){
        this.name = name;
    }

    /**
     * Accessor
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    public boolean isPlaying(){
        return this.isPlaying;
    }

    public void startGamePlayer(){
        this.isPlaying = true;
    }

    // TODO NEEDS TO BE CALLED IN POSTGAMEROUTE
    public void endGamePlayer(){
        this.isPlaying = false;
    }

    /**
     * Changes the logged in status of a player to false.
     */
    public void logout(){
        this.isLogged = false;
    }

    /**
     * Changes the logged in status of a player to true.
     */
    public void login(){
        this.isLogged = true;
    }

    /**
     * Accessor
     * @return the logged in status of a player
     */
    public boolean isLogged(){
        return this.isLogged;
    }
}
