package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Piece.Piece;
import org.junit.jupiter.api.Test;

import java.util.Iterator;


public class BoardViewTest {

    @Test
    public void light_spaces_empty() {
        final BoardView CuT = new BoardView();
        CuT.setBoard();
        Iterator<Row> rowIterator= CuT.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Space> spaceIterator = row.iterator();
            while (spaceIterator.hasNext()) {
                Space space = spaceIterator.next();
                Piece piece = space.getPiece();
                Space.Color color = space.getColor();
                if (color == Space.Color.LIGHT) {
                    assertNull(piece);
                }
            }
        }
    }


    @Test
    public void white_pieces_init() {
        final BoardView CuT = new BoardView();
        CuT.setBoard();
        Iterator<Row> rowIterator= CuT.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int rowIdx = row.getIndex();
            Iterator<Space> spaceIterator = row.iterator();
            while (spaceIterator.hasNext()) {
                Space space = spaceIterator.next();
                Piece piece = space.getPiece();
                Space.Color color = space.getColor();
                if (rowIdx <= 2) {
                    if (color == Space.Color.DARK) {
                        assertEquals(piece.type, Piece.PieceType.SINGLE);
                        assertEquals(piece.color, Piece.PieceColor.WHITE);
                    }
                }
                else {
                    if (piece != null) {
                        assertNotEquals(piece.color, Piece.PieceColor.WHITE);
                    }
                }
            }
        }
    }


    @Test
    public void red_pieces_init() {
        final BoardView CuT = new BoardView();
        CuT.setBoard();
        Iterator<Row> rowIterator= CuT.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int rowIdx = row.getIndex();
            Iterator<Space> spaceIterator = row.iterator();
            while (spaceIterator.hasNext()) {
                Space space = spaceIterator.next();
                Piece piece = space.getPiece();
                Space.Color color = space.getColor();
                if (rowIdx >= 5) {
                    if (color == Space.Color.DARK) {
                        assertEquals(piece.type, Piece.PieceType.SINGLE);
                        assertEquals(piece.color, Piece.PieceColor.RED);
                    }
                }
                else {
                    if (piece != null) {
                        assertNotEquals(piece.color, Piece.PieceColor.RED);
                    }
                }
            }
        }
    }

}
