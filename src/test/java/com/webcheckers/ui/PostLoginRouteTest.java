package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.TemplateEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import com.webcheckers.Application;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.LoginStatus;

import java.util.Hashtable;

/**
 * PostLoginRouteTest is a testing suite for the PostLoginRoute class.
 * @author Dhaval Shrishrimal
 */
public class PostLoginRouteTest {
    /**
     * The component under test (CuT).
     */
    private PostLoginRoute CuT;

    /**
     * Mock classes
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;

    /**
     * Test Attributes
     */
    private String USERW = "user";
    private String PASSW = "pass";
    private String empty = "";

    private static final String TITLE_ATTR = "title";
    private static final String TITLE = "Sign In";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MSG = "message";

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
        playerLobby = mock(PlayerLobby.class);
        playerLobby.sign_in(USERW, PASSW);
        player = mock(Player.class);


        //Put mock playerLobby in our application
        Application.playerLobby = playerLobby;

        // create a unique CuT for each test
        CuT = new PostLoginRoute(engine);
    }

    /**
     * Test for name error.
     */
    @Test
    public void login_game_nameERR(){

        when(request.queryParams(eq(USERNAME))).thenReturn(empty);
        when(request.queryParams(eq(PASSWORD))).thenReturn(empty);

        // Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //call handle on the CuT
        CuT.handle(request, response);

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // assert view model data
        testHelper.assertViewModelAttribute(TITLE_ATTR, TITLE);
        testHelper.assertViewModelAttribute(MSG, PostLoginRoute.NAME_ERR);

        //assert view
        testHelper.assertViewName("signin.ftl");

    }

    /**
     * Test for user already signed in.
     */
    @Test
    public void login_game_signed(){

        when(request.queryParams(eq(USERNAME))).thenReturn(USERW);
        when(request.queryParams(eq(PASSWORD))).thenReturn(PASSW);
        when(playerLobby.sign_in(USERW, PASSW)).thenReturn(USER_ALREADY_LOGIN);

        // Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //call handle on the CuT
        CuT.handle(request, response);

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // assert view model data
        testHelper.assertViewModelAttribute(TITLE_ATTR, TITLE);
        testHelper.assertViewModelAttribute(MSG, PostLoginRoute.ALREADY);

        //assert view
        testHelper.assertViewName("signin.ftl");

    }

    /**
     * Test for checking if the password can be empty.
     */
    @Test
    public void login_game_pass(){

        when(request.queryParams(eq(USERNAME))).thenReturn(USERW);
        when(request.queryParams(eq(PASSWORD))).thenReturn(empty);
        when(playerLobby.sign_in(USERW, empty)).thenReturn(INVALID_PASS_FORMAT);

        // Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //call handle on the CuT
        CuT.handle(request, response);
        System.out.println(Application.playerLobby.sign_in(USERW, empty));

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // assert view model data
        testHelper.assertViewModelAttribute(TITLE_ATTR, TITLE);
        testHelper.assertViewModelAttribute(MSG, PostLoginRoute.PASS);
        //assert view
        testHelper.assertViewName("signin.ftl");

    }

    /**
     * Test for checking if the password is wrong.
     */
    @Test
    public void login_game_pass_wrong(){

        when(request.queryParams(eq(USERNAME))).thenReturn(USERW);
        when(request.queryParams(eq(PASSWORD))).thenReturn(PASSWORD);
        when(playerLobby.sign_in(USERW, PASSWORD)).thenReturn(WRONG_PASS);

        // Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //call handle on the CuT
        CuT.handle(request, response);

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // assert view model data
        testHelper.assertViewModelAttribute(TITLE_ATTR, TITLE);
        testHelper.assertViewModelAttribute(MSG, PostLoginRoute.WRONG);

        //assert view
        testHelper.assertViewName("signin.ftl");

    }

    /**
     * Test for checking if the password is wrong.
     */
    @Test
    public void login_game(){

        when(request.queryParams(eq(USERNAME))).thenReturn(USERW);
        when(request.queryParams(eq(PASSWORD))).thenReturn(PASSW);
        when(playerLobby.sign_in(USERW, PASSW)).thenReturn(NEW_USER_LOGIN);
        Hashtable<String, Player> Players = new Hashtable<>();
        Players.put(USERW, player);
        when(playerLobby.getPlayers()).thenReturn(Players);

        // Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //call handle on the CuT
        CuT.handle(request, response);

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        // assert view model data
        testHelper.assertViewModelAttribute(TITLE_ATTR, TITLE);
        testHelper.assertViewModelAttribute(MSG, PostLoginRoute.WELCOME_MSG);

        //assert view
        testHelper.assertViewName("home.ftl");

    }

}