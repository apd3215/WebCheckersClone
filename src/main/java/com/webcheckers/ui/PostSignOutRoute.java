package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * The UI Controller to GET the login page
 */
public class PostSignOutRoute implements Route {

    private final TemplateEngine templateEngine;
    static final String GET_LOGGED_IN = "isLoggedIn";
    static final String TITLE_ATTR = "title";
    static final String TITLE = "Sign In";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
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
        Map<String, Object> vm = new HashMap<>();
        Session httpSession = request.session();
        httpSession.attribute("Player", null);
        //vm.put(TITLE_ATTR, TITLE);
        response.redirect(HOME_URL);
        //return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        return null;
    }
}
