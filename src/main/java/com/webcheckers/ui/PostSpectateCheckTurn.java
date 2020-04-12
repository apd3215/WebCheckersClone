package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * The UI Controller to Post the spectate checkturn route.
 */
public class PostSpectateCheckTurn implements Route {
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostSpectateCheckTurn(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    /**
     * Handles Spectate checking turn request. Either returns
     * @param request http request
     * @param response http response
     * @return jsonmessage Object
     */
    @Override
    public Object handle(Request request, Response response) {
        return null;

    }
}
