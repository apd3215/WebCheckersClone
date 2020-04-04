package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece.Piece;
import com.webcheckers.util.Message;
import com.webcheckers.model.Move;
import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.WebServer.HOME_URL;

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

    @Override
    public Object handle(Request request, Response response){
        final Session httpSession = request.session();
        Gson gson = new Gson();
        Player player = httpSession.attribute("Player");
        Game game = Application.gameCenter.getGameByPlayer(player);
        Piece.PieceColor callerColor;
        if (player == game.getRedPlayer()){
            callerColor = Piece.PieceColor.RED;
        }
        else {
            callerColor = Piece.PieceColor.WHITE;
        }
        while ( callerColor != game.getActiveColor() ){
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
