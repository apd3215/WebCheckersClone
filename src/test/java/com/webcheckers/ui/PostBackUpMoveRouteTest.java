package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

public class PostBackUpMoveRouteTest {
    /**
     * The component under test (CuT)
     */
    private PostBackUpMoveRoute CuT;

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

        // Application set
        Application.gameCenter = gameCenter;

        // CuT instantiated
        CuT = new PostBackUpMoveRoute(engine);
    }

    @Test
    public void test_true() {
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.backUp()).thenReturn(true);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Object jsonMessage;
        try {
            jsonMessage = CuT.handle(request, response);
        } catch (Exception e) {
            jsonMessage = "EXCEPTION THROWN";
        }
        assertEquals("{\"text\":\"Move Backed Up.\",\"type\":\"INFO\"}", jsonMessage);
    }
    
    @Test
    public void test_false() {
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.backUp()).thenReturn(false);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        Object jsonMessage;
        try {
            jsonMessage = CuT.handle(request, response);
        } catch (Exception e) {
            jsonMessage = "EXCEPTION THROWN";
        }
        assertEquals("{\"text\":\"Cannot Back Up Move.\",\"type\":\"ERROR\"}", jsonMessage);
    }

}

