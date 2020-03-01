
package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.util.Message;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;
import com.webcheckers.model.BoardView;

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
    private static final String ERR = "%s already in game. Select Other Player.";
    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    public PostHomeRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    private static final String TITLE_ATTR = "title";
    private static final String TITLE = "Make game";
    private static final String OTHER = "otherPlayer";
    private static final String MSG = "message";
    private static final String CURR = "currentUser";
    private static final String RED = "redPlayer";
    private static final String WHITE = "whitePlayer";
    private static final String VIEW = "viewMode";
    private static final String BOARD = "board";
    private static final String COLOR = "activeColor";
    private static final String SIGNED = "signed";

    @Override
    public Object handle(Request request, Response response) {

        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        final String otherPlayer = request.queryParams(OTHER);
        Player whitePlayer = Application.playerLobby.getPlayers().get(otherPlayer);
        Game game = Application.playerLobby.getGameByPlayer(whitePlayer);
        if (game != null){
            vm.put(TITLE_ATTR, "Welcome");
            vm.put(MSG, Message.error(String.format(ERR, otherPlayer)));
            vm.put(CURR, httpSession.attribute("Player"));
            vm.put(SIGNED, Application.playerLobby.get_logged_names());
            return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        }
        Player currentPlayer = httpSession.attribute("Player");
        Game newGame = new Game(whitePlayer, currentPlayer);
        Application.playerLobby.addGame(newGame); 
        httpSession.attribute("Game", newGame);

        vm.put(TITLE_ATTR, "Game page!");
        vm.put(MSG, WELCOME_MSG);
        vm.put(CURR, currentPlayer);
        vm.put(RED, currentPlayer);
        vm.put(WHITE, whitePlayer);
        vm.put(VIEW, Game.ViewMode.PLAY);
        vm.put(BOARD, newGame.getBoardView());
        vm.put(COLOR, newGame.getActiveColor());
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
