package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spark.TemplateEngine;
import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.TemplateEngineTester;

/**
 * GetGameRouteTest is a testing suite for the GetGameRoute class.
 * @author Joshua Yoder
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
   // private final TemplateEngineTester testhelper = new TemplateEngineTester();

    /**
     * Test Attributes
     */
    private String USERW = "user";
    private String PASSW = "pass";
    private String empty = "";
    private static final Message NAME_ERR = Message.error("Username cannot be empty AND cannot contain special characters.");
    private static final Message WRONG = Message.error("Wrong Password OR Username already exists.");
    private static final Message PASS = Message.error("Password Cannot be empty.");
    private static final Message ALREADY = Message.error("Player already signed in.");
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

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
        playerLobby.getUsers().put(USERW, PASSW);
        //Put mock playerLobby in our application
        Application.playerLobby = playerLobby;


        // create a unique CuT for each test
        CuT = new PostLoginRoute(engine);
    }

    /**
     * Test for return values.
     */
    @Test
    public void login_game_nameERR(){

        // Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //call handle on the CuT
        CuT.handle(request, response);

        //Test viewmodel
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(TITLE_ATTR, TITLE);





    }

}