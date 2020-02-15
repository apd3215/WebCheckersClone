package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostLoginRoute implements Route {

    static final String USER_NAME = "my_UserName";
    static final String PASSWORD = "my_Password";

    static final String VIEW_NAME = "signin.ftl";

    private final TemplateEngine templateEngine;


    static boolean login(boolean logged_in){
        return logged_in;
    }

    public PostLoginRoute(final TemplateEngine templateEngine){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }


    //Maybe is working
    @Override
    public Object handle(Request request, Response response){
        Map<String, Object> vm = new HashMap<>();

        final String user_name = request.queryParams(USER_NAME);
        final String password = request.queryParams(PASSWORD);

        boolean logged;
        logged = WebServer.sign_in(user_name, password);

        vm.put(GetLoginRoute.GET_LOGGED_IN, logged);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));

    }
}
