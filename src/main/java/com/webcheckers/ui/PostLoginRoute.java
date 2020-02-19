package com.webcheckers.ui;

import spark.Route;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostLoginRoute implements Route {

    private final TemplateEngine templateEngine;
    static final String GET_LOGGED_IN = "isLoggedIn";


    public PostLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    static final String TITLE_ATTR = "title";
    static final String TITLE = "Sign In";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";

    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        final String usernameStr = request.queryParams(USERNAME);
        final String passStr = request.queryParams(PASSWORD);
        boolean logged;
        logged = WebServer.sign_in(usernameStr, passStr);
        vm.put(GetLoginRoute.GET_LOGGED_IN, logged);
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}
