package com.webcheckers.model;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece.Piece;
import com.webcheckers.model.Piece.Piece.PieceColor;
import com.webcheckers.ui.PostLoginRoute;

import java.awt.*;
import java.util.ArrayList;
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


    public void makeMove(Move move){
        Position start = move.getStart();
        Position end = move.getEnd();
        int currRow = start.getRow();
        int currCell = start.getCell();
        int endRow = end.getRow();
        int endCell = end.getCell();
        Space curr = this.boardView.getSpace(currRow, currCell);
        Space end_space = this.boardView.getSpace(endRow, endCell);
        Piece moved = curr.getPiece();
        curr.setPiece(null);
        end_space.setPiece(moved);
        if (this.activeColor == PieceColor.RED){
            this.activeColor = PieceColor.WHITE;
        } else {
            this.activeColor = PieceColor.RED;
        }
    }

    private Boolean isValidNormalMoveSingle(Move move) throws Exception {
        Position start = move.getStart();
        Position end = move.getEnd();
        Space startSpace = boardView.getSpace(start.getRow(), start.getCell());
        Piece startPiece = startSpace.getPiece();
        PieceColor pieceColor = startPiece.color;
        Space endSpace = boardView.getSpace(end.getRow(), end.getCell());
        Piece endPiece = endSpace.getPiece();
        if (endPiece != null) {
            throw new Exception("Piece already present at endPiece");
        }
        boolean validRow;
        boolean validCell;
        if (pieceColor == PieceColor.RED) {
            validRow = start.getRow() - end.getRow() == 1;
            validCell = start.getCell() - end.getCell() == -1 || start.getCell() - end.getCell() == 1;
        }
        else {
            validRow = end.getRow() - start.getRow() == 1;
            validCell = start.getCell() - end.getCell() == -1 || start.getCell() - end.getCell() == 1;
        }

        if (!validRow) {
            throw new Exception("Non-jump must be a difference of 1 row");
        }
        else if (!validCell){
            throw new Exception("Non-jump must be a difference of 1 col");
        }
        else {
            return true;
        }

    }

    private Boolean isValidJumpMoveSingle(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        Space startSpace = boardView.getSpace(start.getRow(), start.getCell());
        Piece startPiece = startSpace.getPiece();
        PieceColor pieceColor = startPiece.color;
        PieceColor myColor;
        PieceColor otherColor;
        int modifier;
        if (pieceColor == PieceColor.RED) {
            modifier = -1;
            myColor = PieceColor.RED;
            otherColor = PieceColor.WHITE;
        }
        else {
            modifier = 1;
            myColor = PieceColor.WHITE;
            otherColor = PieceColor.RED;
        }
        ArrayList<Position> movesToExplore = new ArrayList<>();
        movesToExplore.add(start);
        while (movesToExplore.size() != 0) {
            Position curr = movesToExplore.remove(0);
            int currRow = curr.getRow();
            int currCol = curr.getCell();
            if (end.getRow() == currRow && end.getCell() == currCol){
                return true;
            }
            Space jumpPos1 = boardView.getSpace(currRow + modifier, currCol + 1);
            Space jumpPos2 = boardView.getSpace(currRow + modifier, currCol + 1);
            boolean isJumpPiece1, isJumpPiece2;
            Piece jumpPiece1, jumpPiece2;
            Piece landingPiece1, landingPiece2;
            // there are 2 possible jump directions. -2 and +2 cell change. 2 or -2 row change depending on color
            if (jumpPos1 != null) {
                jumpPiece1 = jumpPos1.getPiece();
                isJumpPiece1 = jumpPiece1.getColor() == otherColor;   //check if there exists a piece to jump
                if (isJumpPiece1) {
                    Space landingPos1 = boardView.getSpace(currRow + (2*modifier), currCol + 2);
                    landingPiece1 = landingPos1.getPiece();
                    if (landingPiece1 == null) {
                        movesToExplore.add(new Position(currRow + (2*modifier), currCol + 2));
                    }
                }
            }
            if (jumpPos2 != null) {
                jumpPiece2 = jumpPos2.getPiece();
                isJumpPiece2 = jumpPiece2.getColor() == otherColor;
                if (isJumpPiece2) {
                    Space landingPos2 = boardView.getSpace(currRow + (2*modifier), currCol - 2);
                    landingPiece2 = landingPos2.getPiece();
                    if (landingPiece2 == null) {
                        movesToExplore.add(new Position(currRow + (2*modifier), currCol - 2));
                    }
                }
            }
        }
        return false;
    }

    public Boolean isMoveValid(Move move) throws Exception {
        Position start = move.getStart();
        Position end = move.getEnd();
        Space startSpace = boardView.getSpace(start.getRow(), start.getCell());
        Space endSpace = boardView.getSpace(end.getRow(), end.getCell());
        Piece startPiece = startSpace.getPiece();
        Piece endPiece = endSpace.getPiece();

        // beginning and end must be on black square
        if (startSpace.getColor() == Space.Color.LIGHT || endSpace.getColor() == Space.Color.LIGHT) {
            throw new Exception("Pieces only on black squares");
        }
        else if (startPiece == null) {
            throw new Exception("No piece on start square");
        }
        else if (endPiece != null) {
            throw new Exception("Already a piece present on end square");
        }

        // only 1 forward or jump
//        boolean jumpMove = isValidJumpMove(move);
        if (startPiece.getType() == Piece.PieceType.SINGLE) {
            if (Math.abs(start.getRow() - end.getRow()) == 1) {
                return isValidNormalMoveSingle(move);
            }
            else {
                return isValidJumpMoveSingle(move);
            }
        }
        else {
            //TODO KING PIECE
            throw new Exception("King move not implemented yet");

        }
    }
}
