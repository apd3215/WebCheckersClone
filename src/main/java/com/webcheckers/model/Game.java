package com.webcheckers.model;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece.PieceColor;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Game {
    private static final Logger LOG = Logger.getLogger(Game.class.getName());

    private Player redPlayer;
    private Player whitePlayer;
    private PieceColor activeColor;
    private BoardView boardView;

    public enum ViewMode {PLAY, SPECTATOR, REPLAY}

    public Game(Player redPlayer, Player whitePlayer) {
        LOG.fine("Game instantiated!");
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = PieceColor.RED;
        this.boardView = new BoardView();
        boardView.setBoard();
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

    public BoardView getBoardView() {
        return this.boardView;
    }

    public Boolean isPlayerInGame(Player player) {
        return redPlayer == player || whitePlayer == player;
    }
}
