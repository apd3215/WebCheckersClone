package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import com.webcheckers.model.Move;
import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import spark.*;

import java.util.Objects;

/**
 * The UI Controller to GET the home page after sign out.
 */
public class PostValidateMoveRoute implements Route {

    private final TemplateEngine templateEngine;

    static final String QP_ACTION_DATA = "actionData";

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     */
    public PostValidateMoveRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    /**
     * Render the WebCheckers login page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the home page
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        String jsonMove = request.queryParams(QP_ACTION_DATA);
        Gson gson = new Gson();
        Move move = gson.fromJson(jsonMove, Move.class);
        Player player = httpSession.attribute(SessionAttributes.PLAYER);
        Game game = Application.gameCenter.getGameByPlayer(player);
        boolean isValid;
        try {
            isValid = game.isMoveValid(move);
            game.makeMove(move);
            Message message = Message.info("Valid move.");
            String jsonMessage = gson.toJson(message);
            game.getTurn().add_move(move);
            response.body(jsonMessage);
            return jsonMessage;
        }
        catch (Exception e ) {
            String failMessage = e.getMessage();
            Message message = Message.error(failMessage);
            String jsonMessage = gson.toJson(message);
            response.body(jsonMessage);
            return jsonMessage;
        }
    }
}
