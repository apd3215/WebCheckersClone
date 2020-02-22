package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.Route;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostLoginRoute implements Route {

    private final TemplateEngine templateEngine;
    static final String GET_LOGGED_IN = "isLoggedIn";
    static final Message NAME_ERR = Message.error("Username cannot be empty AND cannot contain special characters.");
    static final Message WRONG = Message.error("Wrong Password OR Username already exists.");


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
        int logged;
        logged = WebServer.sign_in(usernameStr, passStr);
         // vm.put(GetLoginRoute.GET_LOGGED_IN, logged); ?????
        System.out.println(logged);
        if (logged == 0){
            vm.put("message", NAME_ERR);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == 1 || logged == 2){
            // add more stuff
            return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        } else {
            vm.put("message", WRONG);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
    }
}
