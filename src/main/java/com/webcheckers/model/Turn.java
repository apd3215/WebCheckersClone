package com.webcheckers.model;

import java.util.ArrayList;

/**
 * Represent a turn of a user
 * @author Jonathan Baxley
 * @author Dhaval Shrishrimal
 */
public class Turn {

    private ArrayList<Move> moves;
    private ArrayList<Piece> captured;
    private int row;
    private int col;
    private int num;

    //Default Constructor for turn
    public Turn(){
        this.captured = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.row = 0;
        this.col = 0;
        this.num = 0;
    }

    /**
     * Adds a captured piece
     * @param piece, the captured piece
     */
    public void add_capture(Piece piece){
        captured.add(piece);
    }

    /**
     * Counts how many pieces were covered
     * @return number of captured pieces
     */
    public int getCapturedLen() {
        return captured.size();
    }

    /**
     * Removes a captured piece
     * @return piece that was removed
     */
    public Piece rem_capture(){
        return captured.remove(captured.size() - 1);
    }

    /**
     * Adds a move to list of moves made
     * @param move, move made
     */
    public void add_move(Move move){
        this.moves.add(move);
        num++;
    }

    /**
     * removes a move from list of moves, used if backup is called
     */
    public void remove_move(){
        if (this.moves.size() != 0) {
            this.moves.remove(this.moves.size() - 1);
            num--;
        }
    }

    /**
     * Gets the previously made move, used in backup
     * @return, the previously made move
     */
    public Move getPrevMove(){
        if (this.moves.size() > 0) {
            return this.moves.get(this.moves.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * @return, the current column turn is on
     */
    public int getCol() {
        return col;
    }

    /**
     * @return, the current row turn is on
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets number of moves made
     * @return, number of moves made
     */
    public int getNum() {
        return num;
    }

    /**
     * Sets current row turn is on
     * @param row, current row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets the current column turn is on
     * @param col, current column
     */
    public void setCol(int col) {
        this.col = col;
    }
}
