package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

/**
 * PostSubmitTurnRouteTest is a testing suite for the PostSubmitTurnRoute class.
 * @author Joshua Yoder
 */
public class PostSubmitTurnRouteTest {
    /**
     * The component under test (CuT)
     */
    private PostSubmitTurnRoute CuT;

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
    private Move lastMove;

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
        lastMove = mock(Move.class);

        //Application set
        Application.gameCenter = gameCenter;

        //CuT instantiated
        CuT = new PostSubmitTurnRoute(engine);
    }

    /**
     * Test 
     */
    @Test
    public void test_submit_turn() {
        //Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(session.attribute(SessionAttributes.LAST_MOVE)).thenReturn(lastMove);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        assertEquals("{\"text\":\"Jump moves possible.\",\"type\":\"ERROR\"}", json);
    }
}