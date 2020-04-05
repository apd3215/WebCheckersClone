
package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.util.Message;
import com.webcheckers.appl.LoginStatus;

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
    public static final Message NAME_ERR = Message.error("Username cannot be empty AND cannot contain special characters.");
    public static final Message WRONG = Message.error("Wrong Password OR Username already exists.");
    public static final Message PASS = Message.error("Password Cannot be empty.");
    public static final Message ALREADY = Message.error("Player already signed in.");
    public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    public static final String TITLE = "Sign In";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

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
        vm.put(VMAttributes.TITLE, TITLE);

        final String usernameStr = request.queryParams(USERNAME);
        final String passStr = request.queryParams(PASSWORD);

        LoginStatus logged = Application.playerLobby.sign_in(usernameStr, passStr);

        //Following if statement checks for password / username validation and redirects
        //the use if necessary.
        if (logged == LoginStatus.INVALID_USER_FORMAT){
            vm.put(VMAttributes.MESSAGE, NAME_ERR);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == LoginStatus.USER_ALREADY_LOGIN){
            vm.put(VMAttributes.MESSAGE, ALREADY);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == LoginStatus.INVALID_PASS_FORMAT){
            vm.put(VMAttributes.MESSAGE, PASS);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (logged == LoginStatus.EXISTING_USER_LOGIN || logged == LoginStatus.NEW_USER_LOGIN){
            httpSession.attribute(SessionAttributes.PLAYER, Application.playerLobby.getPlayers().get(usernameStr));
            vm.put(VMAttributes.CURRENT_USER, Application.playerLobby.getPlayers().get(usernameStr));
            vm.put(VMAttributes.MESSAGE, WELCOME_MSG);
            vm.put(VMAttributes.SIGNED, Application.playerLobby.get_logged_names());
            return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        } else {
            vm.put(VMAttributes.MESSAGE, WRONG);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
    }
}