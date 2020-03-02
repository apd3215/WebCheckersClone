package com.webcheckers.model;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.webcheckers.model.Piece.PieceColor;

/**
 * BoardView represents a board of a single game.
 * It is iterable by rows.
 * @author Joe Netti
 * @author Joshua Yoder
 */
public class BoardView implements Iterable<Row> {
    private List<Row> rows = new ArrayList<Row>();

    /**
     * Construct the board view by adding rows and setting the board.
     * Only called when a new game is instantiated.
     */
    public BoardView() {
        for(int i = 0; i < 8; i++) {
            Row row = new Row(i);
            rows.add(row);
        }
        this.setBoard();
    }

    /**
     * Sets the board with red and white pieces for the initial game.
     */
    public void setBoard() {
        for(int i = 0; i < 8; i++) {
            if(i <= 2) {
                rows.get(i).fillRow(PieceColor.WHITE);
            } else if (i >= 5) {
                rows.get(i).fillRow(PieceColor.RED);
            }
        }
    }

    /**
     * Returns an iterator that is the reverse of the board iterator.
     * Useful for generating the view for the white player.
     * @return reverse board iterator (by reverse rows)
     */
    public Iterator<Row> reverseIterator() {
        List<Row> inv = new ArrayList<Row>();
        for (int i = 0; i < 8; i++){
            inv.add(this.rows.get(7-i));
        }
        return inv.iterator();
    }

    /**
     * Return an iterator over the board (rows).
     * @return board iterator (by rows)
     */
    @Override
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

}
