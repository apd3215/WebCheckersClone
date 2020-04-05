package com.webcheckers.ui;

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

/**
 * GetGameRouteTest is a testing suite for the GetGameRoute class.
 * @author Andre Da Costa
 */
public class GetLoginRouteTest {
    private GetLoginRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

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


        // create a unique CuT for each test
        CuT = new GetLoginRoute(engine);
    }

    @Test
    public void testLogIn(){
        //Template engine tester
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Call handle on the CuT
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Sign In");


    }
}
