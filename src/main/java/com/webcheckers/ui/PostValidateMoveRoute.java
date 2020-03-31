package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.util.Message;
import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import spark.*;

import java.util.Objects;

import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * The UI Controller to GET the home page after sign out.
 */
public class PostValidateMoveRoute implements Route {

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     */
    public PostValidateMoveRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    /**
     * Render the WebCheckers login page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the home page
     */
    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        String jsonMove = request.body();
        Gson gson = new Gson();
        Message message = gson.fromJson(jsonMove, Message.class);
        System.out.println(jsonMove);
        System.out.println(message);

//        Player curr = httpSession.attribute("Player");
//        Application.playerLobby.sign_out(curr);
//        httpSession.attribute("Player", null);
//        response.redirect(HOME_URL);
        return null;
    }
}
