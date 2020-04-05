package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.TemplateEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;

/**
 * PostValidateMoveRouteTest is a testing suite for the PostValidateMove class.
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
        CuT = new PostValidateMoveRoute(engine);
    }

    /**
     * Test 
     */
    @Test
    public void test_validMove() {
        //Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute("Player")).thenReturn(player);
        when(session.attribute("last_move")).thenReturn(lastMove);

        //Send an example move
        when(request.queryParams("actionData"))
          .thenReturn("{\"start\":{\"row\":5,\"cell\":2},\"end\":{\"row\":4,\"cell\":3}}");
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        assertEquals("{\"text\":\"Valid move\",\"type\":\"INFO\"}", json);
    }

    @Test
    public void test_invalidMove() throws Exception {
        //Mock attr
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(session.attribute("Player")).thenReturn(player);
        when(session.attribute("last_move")).thenReturn(lastMove);

        //Send an example move
        String jsonMove = "{\"start\":{\"row\":9,\"cell\":9},\"end\":{\"row\":8,\"cell\":8}}";
        when(request.queryParams("actionData"))
          .thenReturn(jsonMove);
        Gson gson = new Gson();
        Move move = gson.fromJson(jsonMove, Move.class);
        when(game.isMoveValid(move)).thenThrow(new Exception("Index request out of bounds"));
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object json = CuT.handle(request, response);

        //System.out.println(json);

        assertEquals("{\"text\":\"Index request out of bounds\",\"type\":\"INFO\"}", json);
    }
}