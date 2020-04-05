package com.webcheckers.ui;

import spark.Route;
import spark.TemplateEngine;
import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The UI Controller to GET the login page
 */
public class GetLoginRoute implements Route {
    
    private final TemplateEngine templateEngine;
    static final String GET_LOGGED_IN = "isLoggedIn";
    static final String TITLE = "Sign In";

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     */
    public GetLoginRoute(final TemplateEngine templateEngine) {
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
    *   the rendered HTML for the login page
    */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put(VMAttributes.TITLE, TITLE);
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}
