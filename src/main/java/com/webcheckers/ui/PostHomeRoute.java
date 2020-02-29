
package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.util.Message;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;
import spark.Route;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostHomeRoute implements Route {

    private final TemplateEngine templateEngine;
    static final Message NAME_ERR = Message.error("Username cannot be empty AND cannot contain special characters.");
    static final Message WRONG = Message.error("Wrong Password OR Username already exists.");
    static final Message PASS = Message.error("Password Cannot be empty.");
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    public PostHomeRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    static final String TITLE_ATTR = "title";
    static final String TITLE = "Sign In";
    static final String otherPlayer = "otherPlayer";

    @Override
    public Object handle(Request request, Response response) {

        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        final String otherPlayer = request.queryParams("otherPlayer");
        System.out.println(otherPlayer);
        Player whitePlayer = Application.playerLobby.getPlayers().get(otherPlayer);
        Player currentPlayer = httpSession.attribute("Player");
        Game newGame = new Game(whitePlayer, currentPlayer);
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
