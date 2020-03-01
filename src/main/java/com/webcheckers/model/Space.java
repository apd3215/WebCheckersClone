package com.webcheckers.model;

public class Space {
    private int cellIdx;
    private Piece piece;

    public Space(int cellIdx) {
        this.cellIdx = cellIdx;
    }

    public boolean isValid() {
        return cellIdx >= 0 && cellIdx <= 7;
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

