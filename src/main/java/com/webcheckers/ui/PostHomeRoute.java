
package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.util.Message;
import com.webcheckers.model.Game;
import com.webcheckers.appl.Player;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.WebServer.GAME_URL;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * The UI controller to the POST home route.
 */
public class PostHomeRoute implements Route {

    private final TemplateEngine templateEngine;
    public static final String ERR = "%s is already in game. Select Other Player.";
    public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    public static final String TITLE_ATTR = "title";
    public static final String TITLE = "Make game";
    public static final String OTHER = "otherPlayer";
    public static final String MSG = "message";
    public static final String CURR = "currentUser";
    public static final String RED = "redPlayer";
    public static final String WHITE = "whitePlayer";
    public static final String VIEW = "viewMode";
    public static final String BOARD = "board";
    public static final String COLOR = "activeColor";
    public static final String SIGNED = "signed";
    
    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     */
    public PostHomeRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    /**
    * WebCheckers post home route (game creation / redirection)
    *
    * @param request
    *   the HTTP request
    * @param response
    *   the HTTP response
    *
    * @return
    *   the rendered HTML for PostHomeRoute (nothing)
    */
    @Override
    public Object handle(Request request, Response response) {


        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();
        final String otherPlayer = request.queryParams(OTHER);
        Player whitePlayer = Application.playerLobby.getPlayer(otherPlayer);
        Game game = Application.playerLobby.getGameByPlayer(whitePlayer);

        //If we have a game, we redirect to home
        if (game != null){
            vm.put(TITLE_ATTR, "Welcome");
            vm.put(CURR, httpSession.attribute("Player"));
            vm.put(SIGNED, Application.playerLobby.get_logged_names());
            httpSession.attribute("error_attr", Message.error(String.format(ERR, otherPlayer)));
            response.redirect(HOME_URL);
            return null;
        }

        //The following occurs (new game is creates)

        Player currentPlayer = httpSession.attribute("Player");
        Game newGame = new Game(currentPlayer, whitePlayer);
        Application.gameCenter.addGame(newGame);
        httpSession.attribute("Game", newGame);

        vm.put(TITLE_ATTR, TITLE);
        vm.put(MSG, WELCOME_MSG);
        vm.put(CURR, currentPlayer);
        vm.put(RED, currentPlayer);
        vm.put(WHITE, whitePlayer);
        vm.put(VIEW, Game.ViewMode.PLAY);
        vm.put(BOARD, newGame.getBoardView());
        vm.put(COLOR, newGame.getActiveColor());

        //Redirect to the game url
        response.redirect(GAME_URL);

        return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }
}
