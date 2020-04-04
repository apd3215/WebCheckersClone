package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import spark.*;

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
        game.makeMove(move);

    }
}
