package com.webcheckers.ui;

import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import spark.Request;
import spark.TemplateEngine;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.WebServer.HOME_URL;


public class GetSpectateStopWatchingRoute implements Route {


    private TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     */
    public GetSpectateStopWatchingRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();



        //Redirect to the game url
        response.redirect(HOME_URL);
        return null;

    }
    
}
