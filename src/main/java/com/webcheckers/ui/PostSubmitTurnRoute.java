package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.*;

public class PostSubmitTurnRoute implements Route {

    private final TemplateEngine templateEngine;

    public PostSubmitTurnRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }


    @Override
    public Object handle(Request request, Response response){
        final Session httpSession = request.session();
        Move move = httpSession.attribute(SessionAttributes.LAST_MOVE);
        httpSession.attribute(SessionAttributes.LAST_MOVE, null);
        Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute(SessionAttributes.PLAYER));
        game.makeMove(move);
        Gson gson = new Gson();
        Message message = Message.info("true");
        String move_json = gson.toJson(message);
        response.body(move_json);
        return move_json;
    }
}
