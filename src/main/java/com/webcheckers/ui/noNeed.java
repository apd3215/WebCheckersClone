package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class noNeed implements Route {

    static final String USERNAME = "my_UserName";
    static final String PASSWORD = "my_Password";

    static final String VIEW_NAME = "signin.ftl";

    private final TemplateEngine templateEngine;


    static boolean login(boolean logged_in){
        return logged_in;
    }

    public noNeed(final TemplateEngine templateEngine){
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }


    //Maybe is working
    @Override
    public Object handle(Request request, Response response){
        Map<String, Object> vm = new HashMap<>();

        //final String username = request.queryParams(USERNAME);
        //final String password = request.queryParams(PASSWORD);

        //System.out.println(username);
        //System.out.println(password);

        boolean logged;
//        logged = WebServer.sign_in(username, password);

//        vm.put(GetLoginRoute.GET_LOGGED_IN, logged);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));

    }
}
