package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Piece.Piece;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

/**
 * BoardViewTest is a test class for BoardView
 * @author Joe Netti
 */
public class BoardViewTest {

    /**
     * Test to ensure light spaces are empty.
     */
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

    /**
     * Test to ensure white pieces are initialized properly.
     */
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

    /**
     * Test to ensure red pieces are initialized properly.
     */
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
