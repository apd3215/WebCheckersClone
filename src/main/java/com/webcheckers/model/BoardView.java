package com.webcheckers.model;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.webcheckers.model.Piece.PieceColor;

public class BoardView implements Iterable<Row> {
    private List<Row> rows = new ArrayList<Row>();

    public BoardView() {
        for(int i = 0; i < 8; i++) {
            Row row = new Row(i);

            rows.add(row);
        }
        this.setBoard();
    }

    public void setBoard() {
        for(int i = 0; i < 8; i++) {
            if(i <= 2) {
                rows.get(i).fillRow(PieceColor.WHITE);
            } else if (i >= 5) {
                rows.get(i).fillRow(PieceColor.RED);
            }
        }
    }

    public Iterator<Row> reverseIterator() {
        List<Row> inv = new ArrayList<Row>();
        for (int i = 0; i < 8; i++){
            inv.add(this.rows.get(7-i));
        }
        return inv.iterator();
    }

    @Override
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

}
