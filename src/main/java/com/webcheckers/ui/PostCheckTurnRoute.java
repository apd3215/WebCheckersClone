package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.util.Message;
import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import spark.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.webcheckers.ui.WebServer.HOME_URL;
import static spark.Spark.get;

/**
 * The UI Controller to the POST checkTurn route
 */

public class PostCheckTurnRoute implements Route {
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostCheckTurnRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    /**
     * Checks to see if the opponent has submitted their turn.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the home page
     */
    @Override
    public Object handle(Request request, Response response) throws InterruptedException{
        final Session httpSession = request.session();
        Gson gson = new Gson();

        Player player = httpSession.attribute("Player");
        Game game = Application.gameCenter.getGameByPlayer(player);
        Piece.PieceColor callerColor;

        if (game == null){
            game = Application.gameCenter.getByID(httpSession.attribute("ID"));
            if (game == null){
                return null;
            }
        }

        if (game != null) {

            if (player == game.getRedPlayer()) {
                callerColor = Piece.PieceColor.RED;
            } else {
                callerColor = Piece.PieceColor.WHITE;
            }
            while (callerColor != game.getActiveColor()) {
                if (game.getIsResigned() != null){
                    Message message = Message.info("true");
                    String jsonMessage = gson.toJson(message);
                    response.body(jsonMessage);
                    return jsonMessage;
                }
                if (game.isGameOver()) {
                    String jsonMessage;
                    if (game.getIsResigned() != null) {
                        Message message = Message.error(game.getIsResigned().getName() + " has resigned. You win! You will now be sent home.");
                        jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        httpSession.attribute("ID", -1);
                    } else {
                        if (game.getWinner().equals(player)){
                            Message message = Message.error("You won. You will now be sent home.");
                            jsonMessage = gson.toJson(message);
                            response.body(jsonMessage);
                            Application.gameCenter.endGame(Application.gameCenter.getGameByPlayer(httpSession.attribute("Player")));
                            httpSession.attribute("ID", -1);
                        }
                        else {
                            Message message = Message.error("You lost. You will now be sent home.");
                            jsonMessage = gson.toJson(message);
                            response.body(jsonMessage);
                            Application.gameCenter.endGame(Application.gameCenter.getGameByPlayer(httpSession.attribute("Player")));
                            httpSession.attribute("ID", -1);
                        }
                    }
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
        Message message = Message.info("true");
        String jsonMessage = gson.toJson(message);
        response.body(jsonMessage);
        get(HOME_URL, new GetHomeRoute(templateEngine)); //Home route (default)
        return jsonMessage;
    }
}