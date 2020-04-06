package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for space object
 * @author Dhaval Shrishrimal
 */
public class SpaceTest {
    /**
     * Component in Testing
     */
    private Space CuT;


    /**
     * Mock Classes
     */
    private int cellIdx;
    private Piece piece;
    private Space.Color color;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        piece = mock(Piece.class);
        CuT = new Space(cellIdx);
    }

    /**
     * Test for cel
     */
    @Test
    public void space_accessors() {
        assertEquals(CuT.getCellIdx(), 0);
    }

    /**
     * Test for getter and setter of piece
     */
    @Test
    public void piece_test(){
        assertEquals(CuT.getPiece(), null);
        CuT.setPiece(piece);
        assertEquals(CuT.getPiece(), piece);
    }

    /**
     * Test for getter and setter of color
     */
    @Test
    public void color_test(){
        assertEquals(CuT.getColor(), null);
        CuT.setColor(Space.Color.DARK);
        assertEquals(CuT.getColor(), Space.Color.DARK);
    }

    /**
     * Test for validity of the space
     */
    @Test
    public void validity_test(){
        assertEquals(CuT.getColor(), null);
        CuT.setColor(Space.Color.DARK);
        assertTrue(CuT.isValid());
        CuT.setColor(Space.Color.LIGHT);
        assertFalse(CuT.isValid());
    }

}
