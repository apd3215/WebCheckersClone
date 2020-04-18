package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Dictionary;
import java.util.Hashtable;

import com.webcheckers.appl.GameCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
public class GetSpectatorGameRouteTest {
    /**
     * The component under test (CuT).
     */
    private GetSpectatorGameRoute CuT;

    /**
     * Mock classes
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private Player spectator;
    private Player playingPlayer;
    private Player playingPlayerB;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Game game;
    private Dictionary<String, Player> players = new Hashtable<>();

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
        spectator = mock(Player.class);
        playingPlayer = mock(Player.class);
        playingPlayerB = mock(Player.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        players.put("player", playingPlayer);
        when(playerLobby.getPlayers()).thenReturn(players);

        //Put mock playerLobby in our application
        Application.playerLobby = playerLobby;
        Application.gameCenter = gameCenter;

        // create a unique CuT for each test
        CuT = new GetSpectatorGameRoute(engine);
    }

    @Test
    public void spectate_game() {
        //Create a game instance with two mock players for testing
        final Game game = new Game(playingPlayer, playingPlayerB);

        //Mock call to playerLobby
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(spectator);
        when(request.queryParams("otherPlayer")).thenReturn("player");
        when(gameCenter.getGameByPlayer(playingPlayer)).thenReturn(game);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        try {
            CuT.handle(request, response);
        } catch (InterruptedException e) {

        }
        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //Test viewmodel attributes
        testHelper.assertViewModelAttribute(VMAttributes.TITLE, GetSpectatorGameRoute.GAME_TITLE);
        testHelper.assertViewModelAttribute(VMAttributes.MESSAGE, GetSpectatorGameRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute(VMAttributes.CURRENT_USER, spectator);
        testHelper.assertViewModelAttribute(VMAttributes.RED_PLAYER, game.getRedPlayer());
        testHelper.assertViewModelAttribute(VMAttributes.WHITE_PLAYER, game.getWhitePlayer());
        testHelper.assertViewModelAttribute(VMAttributes.VIEW_MODE, Game.ViewMode.SPECTATOR);
        testHelper.assertViewModelAttribute(VMAttributes.BOARD, game.getBoardView());
        testHelper.assertViewModelAttribute(VMAttributes.ACTIVE_COLOR, game.getActiveColor());
    }
    
    @Test
    public void spectate_game_isFirst() {
        //Create a game instance with two mock players for testing
        final Game game = new Game(playingPlayer, playingPlayerB);

        //Mock call to playerLobby
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(spectator);
        when(request.queryParams("otherPlayer")).thenReturn("player");
        when(gameCenter.getGameByPlayer(playingPlayer)).thenReturn(game);
        when(session.attribute("isFirst")).thenReturn(true);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        try {
            CuT.handle(request, response);
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void spectate_game_isResigned() {
        //mock game class
        game = mock(Game.class);

        //Mock call to playerLobby
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(spectator);
        when(request.queryParams("otherPlayer")).thenReturn("player");
        when(gameCenter.getGameByPlayer(playingPlayer)).thenReturn(game);
        when(game.getIsResigned()).thenReturn(playingPlayer);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        try {
            CuT.handle(request, response);
        } catch (InterruptedException e) {

        }
    }
    
    @Test
    public void spectate_game_isResignedNotPlayingPlayer() {
        //mock game class
        game = mock(Game.class);

        //Mock call to playerLobby
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(spectator);
        when(request.queryParams("otherPlayer")).thenReturn("player");
        when(gameCenter.getGameByPlayer(playingPlayer)).thenReturn(game);
        when(game.getIsResigned()).thenReturn(playingPlayerB);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        try {
            CuT.handle(request, response);
        } catch (InterruptedException e) {

        }
    }
    
    @Test
    public void spectate_game_gameIsOver() {
        //mock game class
        game = mock(Game.class);

        //Mock call to playerLobby
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(spectator);
        when(request.queryParams("otherPlayer")).thenReturn("player");
        when(gameCenter.getGameByPlayer(playingPlayer)).thenReturn(game);
        when(game.getIsGameOver()).thenReturn(true);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        try {
            CuT.handle(request, response);
        } catch (InterruptedException e) {

        }
    }
    
    @Test
    public void spectate_game_gameIsOverAndPlayingWinner() {
        //mock game class
        game = mock(Game.class);

        //Mock call to playerLobby
        when(session.attribute(SessionAttributes.PLAYER)).thenReturn(spectator);
        when(request.queryParams("otherPlayer")).thenReturn("player");
        when(gameCenter.getGameByPlayer(playingPlayer)).thenReturn(game);
        when(game.getIsGameOver()).thenReturn(true);
        when(game.getWinner()).thenReturn(playingPlayer);

        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        try {
            CuT.handle(request, response);
        } catch (InterruptedException e) {

        }
    }

}