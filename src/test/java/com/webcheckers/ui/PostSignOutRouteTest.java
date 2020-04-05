package com.webcheckers.ui;

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

import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerLobby;

/**
 * PostHomeRouteTest is a testing suite for the GetGameRoute class.
 * @author Joshua Yoder
 */
public class PostSignOutRouteTest {
    /**
     * The component under test (CuT)
     */
    private PostSignOutRoute CuT;

    /**
     * Mock classes
     */
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private Player player;
    private PlayerLobby playerLobby;

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
        //when(playerLobby.sign_out(player)).thenReturn();

        //Application set
        Application.playerLobby = playerLobby;

        //CuT instantiated
        CuT = new PostSignOutRoute(engine);
    }

    /**
     * Test logout
     */
    @Test
    public void test_sign_out() {
        //Mock attr
        when(session.attribute("Player")).thenReturn(player);
        
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);

        //May be able to test better here

        verify(response).redirect("/");
    }
}