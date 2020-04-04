package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Piece.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.TemplateEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;

/**
 * PostHomeRouteTest is a testing suite for the GetGameRoute class.
 * @author Joe Netti
 */
public class PostHomeRouteTest {
    /**
     * The component under test (CuT).
     */
    private PostHomeRoute CuT;

    /**
     * Mock classes
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private Player player;
    private Player otherPlayer;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;

    /**

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        player = mock(Player.class);
        otherPlayer = mock(Player.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);

        //Put mock playerLobby in our application
        Application.playerLobby = playerLobby;
        Application.gameCenter = gameCenter;

        // create a unique CuT for each test
        CuT = new PostHomeRoute(engine);
    }

    /**
     * Test with null game
     */
    @Test
    public void non_existent_game() {
        //Mock call to playerLobby
        when(Application.gameCenter.getGameByPlayer(player)).thenReturn(null);
        when(session.attribute("Player")).thenReturn(player);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //Test
        final Session httpSession = request.session();
        testHelper.assertViewModelAttribute("title", PostHomeRoute.TITLE);
        testHelper.assertViewModelAttribute("message", PostHomeRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("redPlayer", player);
        testHelper.assertViewModelAttribute("whitePlayer", otherPlayer);
        testHelper.assertViewModelAttribute("viewMode", Game.ViewMode.PLAY);
        testHelper.assertViewModelAttribute("activeColor", Piece.PieceColor.RED);
    }

}