package com.webcheckers.model;

import java.lang.Integer;

public class Position {
    private int row;
    private int cell;

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

    @Override
    public String toString() {
        return "Row: " + Integer.toString(this.row) + " Col: " + Integer.toString(this.cell);

    }

}

