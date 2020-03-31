package com.webcheckers.model;

import com.webcheckers.model.Piece.Piece;

/**
 * Represents a single space in the game of webcheckers.
 * @author Joe Netti
 * @author Joshua Yoder
 */
public class Space {
    /**
     * Enum, represents the color of a space
     */
    public enum Color{DARK, LIGHT}

    private int cellIdx;
    private Piece piece;
    private Color color;

    /**
     * Constructor, create a single space.
     * @param cellIdx the index of the space
     */
    public Space(int cellIdx) {
        this.cellIdx = cellIdx;
    }

    /**
     * Check if the space is valid (space is placeable)
     * @return the validity of this space
     */
    public boolean isValid() {
        return this.color == Color.DARK && this.piece == null;
    }

    /**
     * Place a given piece in the space.
     * @param piece the piece to place
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Gets a piece from a space.
     * @return the piece that is in the space
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Gets a piece color.
     * @return the piece that is in the space
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Set the color of a space.
     * @param color the color to set the space to
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Get the index of the cell.
     * @return the index of the cell
     */
    public int getCellIdx() {
        return this.cellIdx;
    }
}

