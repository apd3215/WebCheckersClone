package com.webcheckers.ui;

public class Space {
    private int cellIdx;
    private Piece piece;

    Public Space space(int cellIdx){
        this.cellIdx = cellIdx;
    }

    Public Space space(int cellIdx, Piece piece){
        this.cellIdx = cellIdx;
        this.piece   = piece;
    }

    public boolean isValid() {
        return cellIdx >= 0 && cellInd <= 7;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }
}

