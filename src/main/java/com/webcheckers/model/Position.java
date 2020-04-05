package com.webcheckers.model;

import java.lang.Integer;

/**
 * Represent a Position in the game of webcheckers
 * @author Joe Netti
 */
public class Position {
    //Fields to represent row and column
    private int row;
    private int cell;

    /**
     * Constructor, make a new Position that has starting and ending indexes
     * @param row the row index of the position
     * @param cell the cell index of the position
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Gets the row index of this position
     * @return row index
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the cell index of this position
     * @return cell index
     */
    public int getCell() {
        return this.cell;
    }

    /**
     * String representation of Position
     */
    @Override
    public String toString() {
        return "Row: " + Integer.toString(this.row) + " Col: " + Integer.toString(this.cell);

    }

}

