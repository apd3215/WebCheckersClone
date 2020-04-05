package com.webcheckers.model;

/**
 * Represent a move between two positions in the game of webcheckers
 * @author Joshua Yoder
 */
public class Move {
    //Fields to represent starting and ending positions
    private Position start;
    private Position end;

    /**
     * Constructor, make a new Move that has starting and ending positions
     * @param start the start position
     * @param end the end position
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the starting position of this move
     * @return starting position object
     */
    public Position getStart() {
        return start;
    }

    /**
     * Gets the ending position of this move
     * @return ending position object
     */
    public Position getEnd() {
        return end;
    }

    /**
     * String representation of Move
     */
    @Override
    public String toString() {
        return "Start: " + this.start.toString() + " End: " + this.end.toString();
    }

}