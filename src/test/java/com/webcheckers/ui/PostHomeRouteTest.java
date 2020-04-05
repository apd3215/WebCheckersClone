package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Collections;

/**
 * PostHomeRouteTest is a testing suite for the PostHomeRoute class.
 * @author Joe Netti
 * @author Joshua Yoder
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
    private Game game;

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
        game = mock(Game.class);

        //Create ArrayList of players
        Dictionary<String, Player> players = new Hashtable();
        players.put("player", player);
        players.put("otherPlayer", otherPlayer);
        when(playerLobby.getPlayers()).thenReturn(players);
        when(playerLobby.get_logged_names()).thenReturn(Collections.list(players.keys()));

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
        when(request.queryParams("otherPlayer")).thenReturn("otherPlayer");
        when(gameCenter.getGameByPlayer(player)).thenReturn(null);
        when(session.attribute("Player")).thenReturn(player);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);

        verify(response).redirect("/game");
    }

    /**
     * Test with existing game
     */
    @Test
    public void existent_game() {
        when(request.queryParams("otherPlayer")).thenReturn("otherPlayer");
        when(gameCenter.getGameByPlayer(otherPlayer)).thenReturn(game);
        when(session.attribute("Player")).thenReturn(player);

        CuT.handle(request, response);

        verify(response).redirect("/");
    }
}