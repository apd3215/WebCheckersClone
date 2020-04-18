package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * PlayerTest is a testing suite for the Player class.
 * @author Jonathan Baxley
 */
public class PlayerTest {
    /**
     * The component under test (CuT).
     */
    private Player CuT;

    /**
     * Mock Classes
     */
    private PlayerLobby playerLobby;
    private Game game;
    private String name;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setUp(){
        game = mock(Game.class);
        name = "Test";
        playerLobby = new PlayerLobby();
        CuT = new Player("Test");
    }

    /**
     * Test for a player login
     */
    @Test
    public void login(){
        this.playerLobby.sign_in("Test", "Test");
        assert(CuT.isLogged());
    }

    /**
     * Test for a player logout
     */
    @Test
    public void logout(){
        this.playerLobby.sign_out(CuT);
        assert(!CuT.isLogged());
    }

    /**
     * Test for player getName
     */
    @Test
    public void getName(){
        assert(CuT.getName().equals(name));
    }

    @Test
    public void spectate(){
        assertFalse(CuT.isSpectating());
        CuT.startSpectate(game);
        assertTrue(CuT.isSpectating());
        CuT.stopSpectate(game);
        assertFalse(CuT.isSpectating());
    }

    @Test
    public void start_end_game() {
        assertFalse(CuT.isPlaying());
        CuT.startGamePlayer();
        assertTrue(CuT.isPlaying());
        CuT.endGamePlayer();
        assertFalse(CuT.isPlaying());
    }
}
