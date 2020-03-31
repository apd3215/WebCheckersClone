package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Piece.Piece;

/**
 * Test class for space object
 * author: Andre Da Costa
 */
public class RowTest {
    /**
     * Component in Testing
     */
    private Row CuT;

    private List<Space> spaces;

    /**
     * Mock Classes
     */
    private int Idx;
    private Space space;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        CuT = new Row(0);
        space = mock(Space.class);
    }

    @Test
    public void testFillWhite(){
        CuT.fillRow(Piece.PieceColor.WHITE);
        List<Space> spcs = CuT.getSpaces();
        for(int i = 0; i < 8; i++) {
            if(Idx%2 == 0) {
                if(i%2 != 0) {
                    assertEquals(spcs.get(i).getPiece().getColor(), Piece.PieceColor.WHITE);
                }
            } else {
                if(i%2 == 0) {
                    assertEquals(spcs.get(i).getPiece().getColor(), Piece.PieceColor.WHITE);
                }
            }
        }
    }
    @Test
    public void testFillRed(){
        CuT.fillRow(Piece.PieceColor.RED);
        List<Space> spcs = CuT.getSpaces();
        for(int i = 0; i < 8; i++) {
            if(Idx%2 == 0) {
                if(i%2 != 0) {
                    assertEquals(spcs.get(i).getPiece().getColor(), Piece.PieceColor.RED);
                }
            } else {
                if(i%2 == 0) {
                    assertEquals(spcs.get(i).getPiece().getColor(), Piece.PieceColor.RED);
                }
            }
        }
    }

}
