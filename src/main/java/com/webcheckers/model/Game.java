package com.webcheckers.model;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece.Piece;
import com.webcheckers.model.Piece.Piece.PieceColor;

import java.util.logging.Logger;

/**
 * Represents a single game in webcheckers. Holds some game logic
 * and is instantiated whenever a new game begins.
 * @author Joe Netti
 * @author Joshua Yoder
 */
public class Game {
    private static final Logger LOG = Logger.getLogger(Game.class.getName());
    private Player redPlayer;
    private Player whitePlayer;
    private PieceColor activeColor;
    private BoardView boardView;

    /**
     * Enum representing the view mode (player, spectator, replay)
     */
    public enum ViewMode {PLAY, SPECTATOR, REPLAY}

    /**
     * Instantiate a game with a given red and white player. Sets the board.
     * @param redPlayer the red player in the game
     * @param whitePlayer the white player in the game
     */
    public Game(Player redPlayer, Player whitePlayer) {
        LOG.fine("Game instantiated between" + redPlayer.getName() + " and " + whitePlayer.getName());
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = PieceColor.RED;
        this.boardView = new BoardView();
        boardView.setBoard();
    }

    /**
     * Return the red player.
     * @return the player object corresponding to the red player
     */
    public Player getRedPlayer() {
        return this.redPlayer;
    }

    /**
     * Return the white player.
     * @return the player object corresponding to the white player
     */
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    /**
     * Gets the active PieceColor of the game (red or white)
     * @return the active PieceColor
     */
    public PieceColor getActiveColor() {
        return this.activeColor;
    }

    /**
     * Returns the board view of the game.
     * @author the BoardView
     */
    public BoardView getBoardView() {
        return this.boardView;
    }



    /**
     * Returns weather a player object is in the game.
     * @return weather the player object is in this game.
     */
    public Boolean isPlayerInGame(Player player) {
        return redPlayer == player || whitePlayer == player;
    }


    public Boolean isMoveValid(Move move) throws Exception {
        Position start = move.getStart();
        Position end = move.getEnd();
        Space startSpace = boardView.getSpace(start.getRow(), start.getCell());
        Space endSpace = boardView.getSpace(end.getRow(), end.getCell());
        Piece beginPiece = startSpace.getPiece();
        Piece endPiece = endSpace.getPiece();

        // beginning and end must be on black square
        if (startSpace.getColor() == Space.Color.LIGHT || endSpace.getColor() == Space.Color.LIGHT) {
            throw new Exception("Pieces only on black squares");
        }
        else if (beginPiece == null) {
            throw new Exception("No piece on start square");
        }
        else if (endPiece != null) {
            throw new Exception("Already a piece present on end square");
        }

        PieceColor pieceColor = beginPiece.color;
        // only 1 forward or jump
        if (pieceColor == PieceColor.RED) {
            System.out.println(start.getRow() - end.getRow());
            if (start.getRow() - end.getRow() > 1) {
                throw new Exception("cannot move more than 1 row unless jumping");
            }
        }

        // single piece must move forward
        // cannot jump own pieces
        return true;

    }
}
