package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

/**
 * MoveTest is a testing suite for Move
 * @author Joshua Yoder
 */
public class MoveTest {
    /**
     * The component under test (CuT)
     */
    private Move CuT;

    /**
     * Mock classes
     */
    Position start;
    Position end;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        start = mock(Position.class);
        end = mock(Position.class);

        when(start.toString()).thenReturn("A");
        when(end.toString()).thenReturn("B");

        CuT = new Move(start, end);
    }

    /**
     * Test accessors and mutators
     */
    @Test
    public void test_attr() {
        assertEquals(start, CuT.getStart());
        assertEquals(end, CuT.getEnd());
    }

    /**
     * Smoke test for toString
     */
    @Test
    public void test_toString() {
        assertEquals("Start: A End: B", CuT.toString());
    }
}