package com.webcheckers.model;

import com.webcheckers.appl.Player;
import com.webcheckers.ui.Piece.PieceColor;

public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private PieceColor activeColor;
    public enum ViewMode {PLAY, SPECTATOR, REPLAY}

    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = PieceColor.RED;
    }

    public Player getRedPlayer() {
        return this.redPlayer;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }


    public PieceColor getActiveColor() {
        return this.activeColor;
    }

    public Boolean isPlayerInGame(Player player) {
        return redPlayer == player || whitePlayer == player;
    }

}
