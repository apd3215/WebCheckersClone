package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

/**
 * GetHomeRouteTest is a testing suite for the GetHomeRoute class.
 * @author Jonathan Baxley
 * @author Joshua Yoder
 */
public class GetHomeRouteTest {
    /**
     * The component under test (CuT).
     */
    private GetHomeRoute CuT;

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
    private PlayerLobby playerLobby;
    @Mock
    private ArrayList<String> plist;
    private ArrayList<String> plist2;


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
        game = mock(Game.class);
        player = mock(Player.class);
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);
        MockitoAnnotations.initMocks(this);

        Application.gameCenter = gameCenter;
        Application.playerLobby = playerLobby;

        // create a unique CuT for each test
        CuT = new GetHomeRoute(engine);
    }

    @Test
    public void test_homeNullPlayer(){
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("num", 0);
    }

    @Test
    public void test_home_redirectGame() {
        when(session.attribute("Player")).thenReturn(player);
        when(gameCenter.getGameByPlayer(player)).thenReturn(game);

        CuT.handle(request, response);

        verify(response).redirect("/game");
    }

    @Test
    public void test_newGame_errorAttr() {
        when(session.attribute("Player")).thenReturn(player);
        when(session.attribute("error_attr")).thenReturn("error");
        when(gameCenter.getGameByPlayer(player)).thenReturn(null);
        when(playerLobby.get_logged_names()).thenReturn(plist);
        when(playerLobby.get_playing()).thenReturn(plist2);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Welcome"); //TODO: FIX!!
        testHelper.assertViewModelAttribute("message", "error");
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("signed", plist);
        testHelper.assertViewModelAttribute("playing", plist2);
    }

    @Test
    public void test_newGame_noErrorAttr() {
        when(session.attribute("Player")).thenReturn(player);
        when(session.attribute("error_attr")).thenReturn(null);
        when(gameCenter.getGameByPlayer(player)).thenReturn(null);
        when(playerLobby.get_logged_names()).thenReturn(plist);
        when(playerLobby.get_playing()).thenReturn(plist2);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Welcome"); //TODO: FIX!!
        testHelper.assertViewModelAttribute("message", GetHomeRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("signed", plist);
        testHelper.assertViewModelAttribute("playing", plist2);
    }
}