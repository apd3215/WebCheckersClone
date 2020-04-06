package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece.PieceColor;

/**
 * GameTest is a testing suite for the Game class.
 * @author Joshua Yoder
 */
public class GameTest {
    /**
     * The component under test (CuT).
     */
    private Game CuT;

    /**
     * Mock classes
     */
    private Player redPlayer;
    private Player whitePlayer;
    private Player otherPlayer;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        CuT = new Game(redPlayer, whitePlayer);
    }

    /**
     * Test for Player accessors
     */
    @Test
    public void player_accessors() {
        assertEquals(CuT.getRedPlayer(), redPlayer);
        assertEquals(CuT.getWhitePlayer(), whitePlayer);
    }

    /**
     * Test for seeing if Player in game
     */
    @Test
    public void player_in_game() {
        otherPlayer = mock(Player.class);
        assertTrue(CuT.isPlayerInGame(redPlayer));
        assertTrue(CuT.isPlayerInGame(whitePlayer));
        assertFalse(CuT.isPlayerInGame(otherPlayer));
    }

    /**
     * Test for active color.
     */
    @Test
    public void active_color() {
        assertEquals(CuT.getActiveColor(), PieceColor.RED);
    }
    
    /**
     * Test to ensure boardview is not null.
     */
    @Test
    public void boardview_notnull() {
        assertNotNull(CuT.getBoardView());
    }
}