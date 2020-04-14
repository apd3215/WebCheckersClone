package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.TemplateEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import com.webcheckers.Application;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Turn;
import com.webcheckers.model.Piece.PieceColor;

/**
 * PostSubmitTurnRouteTest is a testing suite for the PostSubmitTurnRoute class.
 * @author Joshua Yoder
 */
public class PostSpectateCheckTurnRouteTest {
    /**
     * The component under test (CuT)
     */
    private PostSpectateCheckTurnRoute CuT;

    /**
     * Mock classes
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private Game game;
    private Player player;
    private GameCenter gameCenter;
    private Turn turn;
    private Move move;

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
        game = mock(Game.class);
        gameCenter = mock(GameCenter.class);
        turn = mock(Turn.class);
        move = mock(Move.class);

        //Application set
        Application.gameCenter = gameCenter;

        //CuT instantiated
        CuT = new PostSpectateCheckTurnRoute(engine);
    }

    /**
     * Test 
     */

    @Test
    public void test_gameNull() {
        //Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute("Playing")).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(null);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", json);
    }
    
    @Test
    public void test_notNull_gameOver() {
        //Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute("Playing")).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.isGameOver()).thenReturn(true);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", json);
    }
    
    @Test
    public void test_notNull_gameNotOver_activeColorIsGameColor() {
        //Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute("Playing")).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.isGameOver()).thenReturn(false);
        when(session.attribute("activeColor")).thenReturn(PieceColor.RED);
        when(game.getActiveColor()).thenReturn(PieceColor.RED);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        assertEquals("{\"text\":\"false\",\"type\":\"INFO\"}", json);
    }
    
    @Test
    public void test_notNull_gameNotOver_activeColorIsNotGameColor() {
        //Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute("Playing")).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.isGameOver()).thenReturn(false);
        when(session.attribute("activeColor")).thenReturn(PieceColor.WHITE);
        when(game.getActiveColor()).thenReturn(PieceColor.RED);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", json);
    }
}