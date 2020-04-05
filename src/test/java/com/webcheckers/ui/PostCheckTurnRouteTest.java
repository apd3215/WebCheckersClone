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
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece.Piece;

/**
 * PostHomeRouteTest is a testing suite for the GetGameRoute class.
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
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);

        //Application set
        Application.playerLobby = playerLobby;
        Application.gameCenter = gameCenter;

        //CuT instantiated
        CuT = new PostCheckTurnRoute(engine);
    }

    /**
     * Test
     */
    @Test
    public void test_instant_resign_red() {
        //Mock attr
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(session.attribute(SessionAttributes.RESIGN)).thenReturn("true");
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);
    }
    
    @Test
    public void test_isActiveColor() {
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(session.attribute(SessionAttributes.RESIGN)).thenReturn("false");
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);

        when(game.getActiveColor()).thenReturn(Piece.PieceColor.RED);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        Object jsonMessage = CuT.handle(request, response);

        assertEquals("{\"text\":\"true\",\"type\":\"INFO\"}", jsonMessage);
    }

    @Test
    public void test_notActiveColor_resignTrue() {
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(session.attribute(SessionAttributes.RESIGN)).thenReturn("false").thenReturn("true");
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);

        when(game.getActiveColor()).thenReturn(Piece.PieceColor.WHITE);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);
    }
    
    @Test
    public void test_notActiveColor_resignFalseThenTrue() {
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(session.attribute(SessionAttributes.RESIGN)).thenReturn("false").thenReturn("false").thenReturn("true");
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(player);

        when(game.getActiveColor()).thenReturn(Piece.PieceColor.WHITE);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);
    }
}