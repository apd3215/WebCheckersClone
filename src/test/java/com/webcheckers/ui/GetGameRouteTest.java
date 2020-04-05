package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.GameCenter;
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
 * GetGameRouteTest is a testing suite for the GetGameRoute class.
 * @author Joshua Yoder
 */
public class GetGameRouteTest {
    /**
     * The component under test (CuT).
     */
    private GetGameRoute CuT;

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
        CuT = new GetGameRoute(engine);
    }

    /**
     * Test for joining an existing game.
     */
    @Test
    public void join_game() {
        //Create a game instance with two mock players for testing
        final Game game = new Game(player, otherPlayer);

        //Mock call to playerLobby
        when(session.attribute("Player")).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //TODO: Replace these magic strings with constants!
        //Test viewmodel attributes
        testHelper.assertViewModelAttribute("title", "Game page!");
        testHelper.assertViewModelAttribute("message", GetGameRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("redPlayer", game.getRedPlayer());
        testHelper.assertViewModelAttribute("whitePlayer", game.getWhitePlayer());
        testHelper.assertViewModelAttribute("viewMode", Game.ViewMode.PLAY);
        testHelper.assertViewModelAttribute("board", game.getBoardView());
        testHelper.assertViewModelAttribute("activeColor", game.getActiveColor());
    }

    /**
     * Test for attempt to join nonexistant game.
     */
    @Test
    public void nonexistant_game() {
        when(session.attribute("Player")).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(null);

        try {
            CuT.handle(request, response);
            fail("Game is not nonexistant!");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test for nonexistant player
     */
    @Test
    public void nonexistant_player() {
        final Game game = new Game(player, otherPlayer);
        when(session.attribute("Player")).thenReturn(null);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);

        try {
            CuT.handle(request, response);
            fail("Player is not nonexistant!");
        } catch (NullPointerException e) {
            //expected
        }
    }
}