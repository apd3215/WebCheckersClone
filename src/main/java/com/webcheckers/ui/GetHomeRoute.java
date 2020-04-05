package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.Application;
import com.webcheckers.model.Game;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.Session;
import spark.ModelAndView;

import com.webcheckers.util.Message;

import static com.webcheckers.ui.WebServer.GAME_URL;

/**
 * The UI Controller to GET the Home page.
 */
public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
    public static final String WELCOME_STR = "Welcome!";

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetHomeRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();

        LOG.finer("GetHomeRoute is invoked.");

        if(httpSession.attribute(SessionAttributes.PLAYER) == null) {
            Map<String, Object> vm = new HashMap<>();
            vm.put(VMAttributes.TITLE, WELCOME_STR);
            // display a user message in the Home page
            vm.put(VMAttributes.MESSAGE, WELCOME_MSG);
            vm.put(VMAttributes.NUM_PLAYERS, Application.playerLobby.getNum_logged_in());
            // render the View
            return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        } else {
            Map<String, Object> vm = new HashMap<>();
            Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute(SessionAttributes.PLAYER));
            if (game != null) {
                response.redirect(GAME_URL);
                return null;
            } else {
                vm.put(VMAttributes.TITLE, WELCOME_STR);
                if (httpSession.attribute(SessionAttributes.ERROR) != null){
                    vm.put(VMAttributes.MESSAGE, httpSession.attribute(SessionAttributes.ERROR));
                    httpSession.attribute(SessionAttributes.ERROR, null);
                } else {
                    vm.put(VMAttributes.MESSAGE, WELCOME_MSG);
                }
                vm.put(VMAttributes.CURRENT_USER, httpSession.attribute(SessionAttributes.PLAYER));
                vm.put(VMAttributes.SIGNED, Application.playerLobby.get_logged_names());
                vm.put(VMAttributes.PLAYING, Application.playerLobby.get_playing());
                return templateEngine.render(new ModelAndView(vm, "home.ftl"));
            }
        }
    }
}
