package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

/**
 * PositionTest is a testing suite for Position 
 * @author Joshua Yoder
 */
public class PositionTest {
    /**
     * The component under test (CuT)
     */
    private Position CuT;

    /**
     * Test accessors and mutators
     */
    @Test
    public void test_attr() {
        CuT = new Position(4,5);
        assertEquals(4, CuT.getRow());
        assertEquals(5, CuT.getCell());
    }

    /**
     * Smoke test for toString
     */
    @Test
    public void test_toString() {
        CuT = new Position(4,5);
        assertEquals("Row: 4 Col: 5", CuT.toString());
    }
}