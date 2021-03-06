package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import spark.*;

import java.util.Objects;

import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * The UI Controller to GET the home page after sign out.
 */
public class PostSignOutRoute implements Route {

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     */
    public PostSignOutRoute(final TemplateEngine templateEngine) {
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
        Player curr = httpSession.attribute(SessionAttributes.PLAYER);
        Application.playerLobby.sign_out(curr);
        httpSession.attribute(SessionAttributes.PLAYER, null);
        response.redirect(HOME_URL);
        return null;
    }
}
