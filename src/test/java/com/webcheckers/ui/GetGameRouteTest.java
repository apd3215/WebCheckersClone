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
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //Test viewmodel attributes
        testHelper.assertViewModelAttribute(VMAttributes.TITLE, GetGameRoute.GAME_TITLE);
        testHelper.assertViewModelAttribute(VMAttributes.MESSAGE, GetGameRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute(VMAttributes.CURRENT_USER, player);
        testHelper.assertViewModelAttribute(VMAttributes.RED_PLAYER, game.getRedPlayer());
        testHelper.assertViewModelAttribute(VMAttributes.WHITE_PLAYER, game.getWhitePlayer());
        testHelper.assertViewModelAttribute(VMAttributes.VIEW_MODE, Game.ViewMode.PLAY);
        testHelper.assertViewModelAttribute(VMAttributes.BOARD, game.getBoardView());
        testHelper.assertViewModelAttribute(VMAttributes.ACTIVE_COLOR, game.getActiveColor());
    }

    /**
     * Test for attempt to join nonexistant game.
     */
    @Test
    public void nonexistant_game() {
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(null);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);

        verify(response).redirect(WebServer.HOME_URL);
    }
}