package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

/**
 * GameCenterTest is a testing suite for GameCenter
 * @author Joshua Yoder
 */
public class GameCenterTest {
    /**
     * The component under test (CuT)
     */
    private GameCenter CuT;

    /**
     * Mock classes
     */
    Player redPlayer;
    Player whitePlayer;
    Player notInGame;
    Game game;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        notInGame = mock(Player.class);
        game = mock(Game.class);

        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.isPlayerInGame(redPlayer)).thenReturn(true);
        when(game.isPlayerInGame(whitePlayer)).thenReturn(true);

        CuT = new GameCenter();
    }

    @Test
    public void test_getGame() {
        CuT.addGame(game);
        assertEquals(game, CuT.getGame(redPlayer, whitePlayer));
    }

    @Test
    public void test_getGameByPlayer() {
        CuT.addGame(game);
        assertEquals(game, CuT.getGameByPlayer(redPlayer));
        assertEquals(game, CuT.getGameByPlayer(whitePlayer));
        assertNull(CuT.getGameByPlayer(notInGame));
    }

    @Disabled("Function may not be implemented properly.")
    @Test
    public void test_endGame() {
        CuT.addGame(game);
        assertEquals(game, CuT.getGame(redPlayer, whitePlayer));
        CuT.endGame(game);
        assertNull(CuT.getGame(redPlayer, whitePlayer));
    }
}