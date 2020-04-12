package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.util.Message;
import spark.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * The UI Controller to Post the spectate checkturn route.
 */
public class PostSpectateCheckTurnRoute implements Route {
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostSpectateCheckTurnRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    /**
     * Handles Spectate checking turn request. Either returns
     * @param request http request
     * @param response http response
     * @return jsonmessage Object
     */
    @Override
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        Gson gson = new Gson();
        Message message;
        Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute("Playing"));

        Piece.PieceColor activeColor = httpSession.attribute("activeColor");
        Piece.PieceColor gameColor = game.getActiveColor();

        if (activeColor == gameColor){
            message = Message.info("false");
            String move_json = gson.toJson(message);
            response.body(move_json);
            return move_json;
        }
        else{
            message = Message.info("true");
            String move_json = gson.toJson(message);
            response.body(move_json);
            return move_json;
        }
    }
}
