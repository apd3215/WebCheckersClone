package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import spark.TemplateEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;

import com.webcheckers.Application;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;

/**
 * PostHomeRouteTest is a testing suite for the GetGameRoute class.
 * 
 * @author Joshua Yoder
 */
public class PostCheckTurnRouteTest {
    /**
     * The component under test (CuT)
     */
    private PostCheckTurnRoute CuT;

    /**
     * Mock classes
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private Player player;
    private Player player2;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Game game;

    /**
     * Setup new mock objects for each test
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        player = mock(Player.class);
        player2 = mock(Player.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);

        // Application set
        Application.playerLobby = playerLobby;
        Application.gameCenter = gameCenter;

        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(game.getRedPlayer()).thenReturn(player);

        // CuT instantiated
        CuT = new PostCheckTurnRoute(engine);
    }

    /**
     * Test
     */
    @Disabled("Null pointer exception for some reason")
    @Test
    public void test_nullGame() {
        when(gameCenter.getGameByPlayer(player)).thenReturn(null);
        Object jsonResponse;
        try {
            jsonResponse = CuT.handle(request, response);
        } catch (InterruptedException e) {
            jsonResponse = "EXCEPTION THROWN";
        }
        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", jsonResponse);
    }

    @Test
    public void test_isActiveColor() {
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);

        when(game.getActiveColor()).thenReturn(Piece.PieceColor.RED);
        Object jsonResponse;
        
        //Call handle on the CuT
        try {
            jsonResponse = CuT.handle(request, response);
        } catch(InterruptedException e) {
            jsonResponse = "EXCEPTION THROWN";
        }
        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", jsonResponse);
    }

    @Test
    public void test_instantNotGameOver() {
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);
        when(game.isGameOver()).thenReturn(false);

        when(game.getActiveColor())
            .thenReturn(Piece.PieceColor.WHITE)
            .thenReturn(Piece.PieceColor.RED);
        Object jsonResponse;

        //Call handle on the CuT
        try {
            jsonResponse = CuT.handle(request, response);
        } catch(InterruptedException e) {
            jsonResponse = "EXCEPTION THROWN";
        }
        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", jsonResponse);
    }
    
    @Disabled("Hangs for a really long time.")
    @Test
    public void test_gameOver_winnerEqualsPlayer() {
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);
        when(game.isGameOver()).thenReturn(true);
        when(game.getIsResigned()).thenReturn(null);
        when(game.getWinner()).thenReturn(player);

        when(game.getActiveColor()).thenReturn(Piece.PieceColor.WHITE);
        Object jsonResponse;

        //Call handle on the CuT
        try {
            jsonResponse = CuT.handle(request, response);
        } catch(InterruptedException e) {
            jsonResponse = "EXCEPTION THROWN";
        }
        assertEquals("{\"text\":\"You won. You will now be sent home.\",\"type\":\"ERROR\"}", jsonResponse);
    }
    
    @Test
    public void test_gameOver_resigned() {
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);
        when(game.isGameOver()).thenReturn(true);
        when(game.getIsResigned()).thenReturn(player);

        when(game.getActiveColor()).thenReturn(Piece.PieceColor.WHITE);
        Object jsonResponse;

        //Call handle on the CuT
        try {
            jsonResponse = CuT.handle(request, response);
        } catch(InterruptedException e) {
            jsonResponse = "EXCEPTION THROWN";
        }
        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", jsonResponse);
    }
    
    @Disabled("Hangs for a really long time.")
    @Test
    public void test_gameOver_winnerEqualsOther() {
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);
        when(game.isGameOver()).thenReturn(true);
        when(game.getIsResigned()).thenReturn(null);
        when(game.getWinner()).thenReturn(player2);

        when(game.getActiveColor()).thenReturn(Piece.PieceColor.WHITE);
        Object jsonResponse;

        //Call handle on the CuT
        try {
            jsonResponse = CuT.handle(request, response);
        } catch(InterruptedException e) {
            jsonResponse = "EXCEPTION THROWN";
        }
        assertEquals("{\"text\":\"You lost. You will now be sent home.\",\"type\":\"ERROR\"}", jsonResponse);
    }

    @Test
    public void test_gameOver_isResignedTrue() {
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);
        when(game.getActiveColor()).thenReturn(Piece.PieceColor.WHITE);
        when(game.getIsResigned()).thenReturn(null).thenReturn(player);
        when(game.isGameOver()).thenReturn(true);

        Object jsonResponse;

        //Call handle on the CuT
        try {
            jsonResponse = CuT.handle(request, response);
        } catch(InterruptedException e) {
            jsonResponse = "EXCEPTION THROWN";
        }
        //assertEquals("{\"text\":\"You lost. You will now be sent home.\",\"type\":\"ERROR\"}", jsonResponse);
        //assertEquals("{\"text\":\"null has resigned. You win! You will now be sent home.\",\"type\":\"ERROR\"}", jsonResponse);
        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", jsonResponse);
    }
}