package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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
    Player spectator;
    Game game;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        notInGame = mock(Player.class);
        spectator = mock(Player.class);
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
    
    @Test
    public void test_getGameBySpectator() {
        CuT.addGame(game);
        ArrayList<Player> spectators = new ArrayList<>();
        spectators.add(spectator);
        spectators.add(redPlayer);
        spectators.add(whitePlayer);
        CuT.addGame(game);
        CuT.endGame(game);
        when(game.getSpectators()).thenReturn(spectators);
        assertEquals(game, CuT.getBySpectator(spectator));
    }
    
    @Test
    public void test_getGameBySpectator_notIn() {
        CuT.addGame(game);
        ArrayList<Player> spectators = new ArrayList<>();
        spectators.add(redPlayer);
        spectators.add(whitePlayer);
        CuT.addGame(game);
        CuT.endGame(game);
        when(game.getSpectators()).thenReturn(spectators);
        assertEquals(null, CuT.getBySpectator(spectator));
    }

    @Test
    public void test_getGameBySpectator_null() {
        CuT.addGame(game);
        ArrayList<Player> spectators = new ArrayList<>();
        spectators.add(spectator);
        spectators.add(redPlayer);
        spectators.add(whitePlayer);
        CuT.addGame(game);
        when(game.getSpectators()).thenReturn(spectators);
        assertEquals(null, CuT.getBySpectator(spectator));
    }
    
    @Test
    public void test_getGameByID() {
        CuT.addGame(game);
        CuT.endGame(game);
        assertEquals(game, CuT.getByID(0));
    }

    @Test
    public void test_getGameByID_notIn() {
        CuT.addGame(game);
        CuT.endGame(game);
        assertEquals(null, CuT.getByID(1));
    }

    
    @Test
    public void test_getGameByID_null() {
        assertEquals(null, CuT.getByID(0));
    }

    @Test
    public void test_endGame() {
        CuT.addGame(game);
        assertEquals(game, CuT.getGame(redPlayer, whitePlayer));
        CuT.endGame(game);
    }
    
    @Test
    public void test_endGame_null() {
        CuT.endGame(null);
    }

}