package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import com.webcheckers.Application;
import spark.*;

public class PostBackUpMoveRoute implements Route {

    private TemplateEngine templateEngine;

    public PostBackUpMoveRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    // After a move is validated and before it's submitted, once backup is pressed, restore board to what it was
    // at the beginning of the turn.
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        //String jsonMove = request.queryParams("actionData");
        Gson gson = new Gson();
        Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute("Player"));
        boolean b = game.backUp();
        Message message;
        if (b) {
            message = Message.info("Move Backed Up.");
        } else {
            message = Message.error("Cannot Back Up Move.");
        }
        String jsonMessage = gson.toJson(message);
        response.body(jsonMessage);
        return jsonMessage;
    }
}
