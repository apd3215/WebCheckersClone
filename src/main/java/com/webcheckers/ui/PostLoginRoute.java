
package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.util.Message;

import spark.Route;
import spark.TemplateEngine;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostLoginRoute implements Route {

    private final TemplateEngine templateEngine;
    static final Message NAME_ERR = Message.error("Username cannot be empty AND cannot contain special characters.");
    static final Message WRONG = Message.error("Wrong Password OR Username already exists.");
    static final Message PASS = Message.error("Password Cannot be empty.");
    static final Message ALREADY = Message.error("Player already signed in.");
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    public PostLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    static final String TITLE_ATTR = "title";
    static final String TITLE = "Sign In";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";

    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        final String usernameStr = request.queryParams(USERNAME);
        final String passStr = request.queryParams(PASSWORD);
        int logged;
        logged = Application.playerLobby.sign_in(usernameStr, passStr);
        if (logged == 0){
            vm.put("message", NAME_ERR);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == -2){
            vm.put("message", ALREADY);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == -1){
            vm.put("message", PASS);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == 2 || logged == 1){
            httpSession.attribute("Player", Application.playerLobby.getPlayers().get(usernameStr));
            vm.put("currentUser", Application.playerLobby.getPlayers().get(usernameStr));
            vm.put("message", WELCOME_MSG);
            vm.put("signed", Application.playerLobby.get_logged_names());
            return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        } else {
            vm.put("message", WRONG);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
    }
}
