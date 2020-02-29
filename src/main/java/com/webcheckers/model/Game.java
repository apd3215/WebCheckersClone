package com.webcheckers.model;

import com.webcheckers.appl.Player;

public class Game {

    private Player redPlayer;
    private Player whitePlayer;

    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
    }

    public Player getRedPlayer() {
        return this.redPlayer;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

}
