package com.webcheckers.appl;

import com.webcheckers.model.Game;

/**
 * Player class, represents a player in webcheckers.
 * @author Joe Netti
 * @author Joshua Yoder
 * @author Jonathan Baxley
 * @author Dhaval Shrishrimal
 */
public class Player {
    //Player fields
    private boolean isSpectating = false;
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

    public boolean isSpectating() {
        return isSpectating;
    }

    public void startSpectate(Game game){
        this.isSpectating = true;
        game.getSpectators().add(this);
    }

    public void stopSpectate(Game game){
        this.isSpectating = false;
        game.getSpectators().remove(this);
    }

    /**
     * Accessor
     * @return weather the player is playing
     */
    public boolean isPlaying(){
        return this.isPlaying;
    }

    /**
     * Mutator, sets playing status for player
     */
    public void startGamePlayer(){
        this.isPlaying = true;
    }

    /**
     * Mutator, sets game not playing for a player
     */
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