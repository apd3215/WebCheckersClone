package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece.Piece;
import com.webcheckers.util.Message;
import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import spark.*;

import java.util.Objects;

import static com.webcheckers.ui.WebServer.HOME_URL;
import static spark.Spark.get;

/**
 * The UI Controller to the POST checkTurn route
 */

public class PostCheckTurnRoute implements Route {
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     */
    public PostCheckTurnRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    /**
     * Checks to see if the opponent has submitted their turn.
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
    public Object handle(Request request, Response response){
        final Session httpSession = request.session();
        Gson gson = new Gson();

        if(httpSession.attribute(SessionAttributes.RESIGN) == null){
            Player player = httpSession.attribute(SessionAttributes.PLAYER);
            Game game = Application.gameCenter.getGameByPlayer(player);
            Piece.PieceColor callerColor;

            if (game != null) {
                if (player == game.getRedPlayer()) {
                    callerColor = Piece.PieceColor.RED;
                } else {
                    callerColor = Piece.PieceColor.WHITE;
                }
                while (callerColor != game.getActiveColor()) {
                    if (game.isGameOver()){
                        Message message = Message.info("true");
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        Application.gameCenter.endGame(Application.gameCenter.getGameByPlayer(httpSession.attribute("Player")));
                        get(HOME_URL, new GetHomeRoute(templateEngine)); //Home route (default)
                        return jsonMessage;
                    }
                    Message message = Message.info("false");
                    String jsonMessage = gson.toJson(message);
                    response.body(jsonMessage);
                }
                Message message = Message.info("true");
                String jsonMessage = gson.toJson(message);
                response.body(jsonMessage);
                return jsonMessage;
            }
        }

        Message message = Message.info("true");
        String jsonMessage = gson.toJson(message);
        response.body(jsonMessage);
        Application.gameCenter.endGame(Application.gameCenter.getGameByPlayer(httpSession.attribute("Player")));
        get(HOME_URL, new GetHomeRoute(templateEngine)); //Home route (default)
        return jsonMessage;
    }
}
