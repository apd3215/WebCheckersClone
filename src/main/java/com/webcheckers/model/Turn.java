package com.webcheckers.model;

import com.webcheckers.model.Piece.Piece;

import java.util.ArrayList;

public class Turn {

    private ArrayList<Move> moves;
    private ArrayList<Piece> captured;
    private int row;
    private int col;
    private int num;

    public Turn(){
        this.captured = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.row = 0;
        this.col = 0;
        this.num = 0;
    }

    public void add_capture(Piece piece){
        captured.add(piece);
    }

    public Piece rem_capture(){
        return captured.remove(captured.size() - 1);
    }

    public void add_move(Move move){
        this.moves.add(move);
        num++;
    }

    public void remove_move(){
        this.moves.remove(this.moves.size() - 1);
        num--;
    }

    public Move getPrevMove(){
        if (this.moves.size() > 0) {
            return this.moves.get(this.moves.size() - 1);
        } else {
            return null;
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getNum() {
        return num;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
