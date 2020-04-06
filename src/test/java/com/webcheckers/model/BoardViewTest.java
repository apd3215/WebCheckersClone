package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

/**
 * BoardViewTest is a test class for BoardView
 * @author Joe Netti
 * @author Joshua Yoder
 */
public class BoardViewTest {

    private BoardView CuT;

    @BeforeEach
    public void setup() {
        CuT = new BoardView();
        CuT.setBoard();
    }

    /**
     * Test to ensure light spaces are empty.
     */
    @Test
    public void light_spaces_empty() {
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
                        assertEquals(Piece.PieceType.SINGLE, piece.type);
                        assertEquals(Piece.PieceColor.WHITE, piece.color);
                    }
                }
                else {
                    if (piece != null) {
                        assertNotEquals(Piece.PieceColor.WHITE, piece.color);
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
                        assertEquals(Piece.PieceType.SINGLE, piece.type);
                        assertEquals(Piece.PieceColor.RED, piece.color);
                    }
                }
                else {
                    if (piece != null) {
                        assertNotEquals(Piece.PieceColor.RED, piece.color);
                    }
                }
            }
        }
    }

    /**
     * Ensure reverse iterator functions as intended.
     * Also does a count of iterated spaces and pieces.
     */
    @Test
    public void test_reverseIterator() {
        int numSpaces = 0;
        int numPieces = 0;
        Iterator<Row> rowIterator = CuT.reverseIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int rowIdx = row.getIndex();
            Iterator<Space> spaceIterator = row.reverseIterator();
            while (spaceIterator.hasNext()) {
                Space space = spaceIterator.next();
                Piece piece = space.getPiece();
                Space.Color color = space.getColor();
                numSpaces++;
                if (color == Space.Color.LIGHT) {
                    assertNull(piece);
                } else if (piece != null) {
                    numPieces++;
                    if (rowIdx <= 2) {
                        assertEquals(Piece.PieceType.SINGLE, piece.type);
                        assertEquals(Piece.PieceColor.WHITE, piece.color);
                    } else {
                        assertEquals(Piece.PieceType.SINGLE, piece.type);
                        assertEquals(Piece.PieceColor.RED, piece.color);
                    }
                }
            }
        }
        assertEquals(24, numPieces);
        assertEquals(64, numSpaces);
    }

    /**
     * Test to ensure getSpace works
     */
    @Test
    public void test_getSpace() {
        int i = 0;
        int j = 0;

        Iterator<Row> rowIterator= CuT.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Space> spaceIterator = row.iterator();
            while (spaceIterator.hasNext()) {
                assertEquals(spaceIterator.next(), CuT.getSpace(i,j));
                j++;
            }
            j = 0;
            i++;
        }
    }
}
