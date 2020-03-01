package com.webcheckers.model;

public class Space {
    public enum Color{DARK, LIGHT}


    private int cellIdx;
    private Piece piece;
    private Color color;


    public Space(int cellIdx) {
        this.cellIdx = cellIdx;
    }

    public boolean isValid() {
        return this.color == Color.DARK && this.piece == null;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }
}

