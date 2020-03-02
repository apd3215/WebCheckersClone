
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

/**
 * The UI controller to the POST login route.
 */
public class PostLoginRoute implements Route {

    private final TemplateEngine templateEngine;
    private static final Message NAME_ERR = Message.error("Username cannot be empty AND cannot contain special characters.");
    private static final Message WRONG = Message.error("Wrong Password OR Username already exists.");
    private static final Message PASS = Message.error("Password Cannot be empty.");
    private static final Message ALREADY = Message.error("Player already signed in.");
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private static final String TITLE_ATTR = "title";
    private static final String TITLE = "Sign In";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MSG = "message";

    public PostLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }
    
    /**
    * WebCheckers post login route
    *
    * @param request
    *   the HTTP request
    * @param response
    *   the HTTP response
    *
    * @return
    *   the rendered HTML for PostLoginRoute
    */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);

        final String usernameStr = request.queryParams(USERNAME);
        final String passStr = request.queryParams(PASSWORD);

        int logged = Application.playerLobby.sign_in(usernameStr, passStr);

        //Following if statement checks for password / username validation and redirects
        //the use if necessary.
        if (logged == 0){
            vm.put(MSG, NAME_ERR);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == -2){
            vm.put(MSG, ALREADY);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == -1){
            vm.put(MSG, PASS);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == 2 || logged == 1){
            httpSession.attribute("Player", Application.playerLobby.getPlayers().get(usernameStr));
            vm.put("currentUser", Application.playerLobby.getPlayers().get(usernameStr));
            vm.put(MSG, WELCOME_MSG);
            vm.put("signed", Application.playerLobby.get_logged_names());
            return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        } else {
            vm.put(MSG, WRONG);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
    }
}
