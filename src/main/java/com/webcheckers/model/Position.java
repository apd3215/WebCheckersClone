package com.webcheckers.model;

public class Position {
    private int row;
    private int cell;

    // TODO: check row and cell are between 0,7
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return this.row;
    }

    public int getCell() {
        return this.cell;
    }
}

