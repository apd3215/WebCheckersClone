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
import com.webcheckers.model.Turn;

/**
 * PostValidateMoveRouteTest is a testing suite for the PostValidateMove class.
 * 
 * @author Joshua Yoder
 */
public class PostValidateMoveRouteTest {
    /**
     * The component under test (CuT)
     */
    private PostValidateMoveRoute CuT;

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
    private Move move;
    private Turn turn;

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

        // Application set
        Application.gameCenter = gameCenter;

        // CuT instantiated
        CuT = new PostValidateMoveRoute(engine);
    }

    /**
     * Test
     */
    @Test
    public void test_validMove() {
        // Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);

        //Example move injection, not actually used.
        when(request.queryParams(PostValidateMoveRoute.QP_ACTION_DATA))
                .thenReturn("{\"start\":{\"row\":5,\"cell\":2},\"end\":{\"row\":4,\"cell\":3}}");
        //Move will always be valid, isMoveValid is not tested here.
        try {
            when(game.isMoveValid(any(Move.class))).thenReturn(true);
        } catch (Exception e) {
            // Will never reach
        }
        when(game.getTurn()).thenReturn(turn);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        assertEquals("{\"text\":\"Valid move.\",\"type\":\"INFO\"}", json);
    }

    @Test
    public void test_invalidMove() throws Exception {
        //Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);

        //Send an example move
        String jsonMove = "{\"start\":{\"row\":9,\"cell\":9},\"end\":{\"row\":8,\"cell\":8}}";
        when(request.queryParams(PostValidateMoveRoute.QP_ACTION_DATA)).thenReturn(jsonMove);
        //This will suffice, isMoveValid only called once (on invalid move).
        when(game.isMoveValid(any(Move.class))).thenThrow(new Exception("Index request out of bounds"));
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        assertEquals("{\"text\":\"Index request out of bounds\",\"type\":\"ERROR\"}", json);
    }
}