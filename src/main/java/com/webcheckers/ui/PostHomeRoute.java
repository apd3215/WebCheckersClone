
package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.util.Message;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;

import spark.Route;
import spark.TemplateEngine;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.ModelAndView;

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
    static final String TITLE = "Make game";
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
        Application.playerLobby.addGame(newGame); 
        httpSession.attribute("Game", newGame);

        vm.put("title", "Game page!");
        vm.put("message", WELCOME_MSG);
        vm.put("currentUser", currentPlayer);
        vm.put("redPlayer", currentPlayer);
        vm.put("whitePlayer", whitePlayer);
        vm.put("viewMode", Game.ViewMode.PLAY);
        vm.put("board", newGame.getBoardView());
        vm.put("activeColor", newGame.getActiveColor());
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
