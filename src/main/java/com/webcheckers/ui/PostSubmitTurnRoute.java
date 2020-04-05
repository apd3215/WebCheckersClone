package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.*;

import static spark.Spark.post;

public class PostSubmitTurnRoute implements Route {

    private final TemplateEngine templateEngine;

    public PostSubmitTurnRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }


    @Override
    public Object handle(Request request, Response response){
        final Session httpSession = request.session();
        Move move = httpSession.attribute("last_move");
        httpSession.attribute("last_move", null);
        Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute("Player"));
        Gson gson = new Gson();
        Message message;
        if (game.makeMove(move)) {
            message = Message.info("true");
            String move_json = gson.toJson(message);
            response.body(move_json);
            return move_json;
        } else {
            message = Message.error("Jump moves possible.");
            String move_json = gson.toJson(message);
            response.body(move_json);
            post("/backup", new PostBackUpMoveRoute(templateEngine));
            return move_json;
        }

    }
}
