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
    private static final String ERR = "%s is already in game. Select Other Player.";
    public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    public static final String TITLE_STR = "Welcome";

    public static final String TITLE = "Make game";
    public static final String OTHER = "otherPlayer";

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
        Player whitePlayer = Application.playerLobby.getPlayers().get(otherPlayer);
        Game game = Application.gameCenter.getGameByPlayer(whitePlayer);

        //If we have a null game, we redirect to home
        if (game != null){
            vm.put(VMAttributes.TITLE, TITLE_STR);
            vm.put(VMAttributes.CURRENT_USER, httpSession.attribute(SessionAttributes.PLAYER));
            vm.put(VMAttributes.SIGNED, Application.playerLobby.get_logged_names());
            vm.put(VMAttributes.PLAYING, Application.playerLobby.get_playing());
            vm.put(VMAttributes.SPECTATING, Application.playerLobby.get_spectating());
            httpSession.attribute(SessionAttributes.ERROR, Message.error(String.format(ERR, otherPlayer)));
            response.redirect(HOME_URL);
            return null;
        }

        //The following occurs (new game is created)

        Player currentPlayer = httpSession.attribute(SessionAttributes.PLAYER);
        Game newGame = new Game(currentPlayer, whitePlayer);
        Application.gameCenter.addGame(newGame);
        httpSession.attribute(SessionAttributes.GAME, newGame);

        vm.put(VMAttributes.TITLE, TITLE);
        vm.put(VMAttributes.MESSAGE, WELCOME_MSG);
        vm.put(VMAttributes.CURRENT_USER, currentPlayer);
        vm.put(VMAttributes.RED_PLAYER, currentPlayer);
        vm.put(VMAttributes.WHITE_PLAYER, whitePlayer);
        vm.put(VMAttributes.VIEW_MODE, Game.ViewMode.PLAY);
        vm.put(VMAttributes.BOARD, newGame.getBoardView());
        vm.put(VMAttributes.ACTIVE_COLOR, newGame.getActiveColor());

        //Redirect to the game url
        response.redirect(GAME_URL);

        return null;
    }
}