package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.*;

import static spark.Spark.get;
import static spark.route.HttpMethod.get;

/**
 * UI controller for POST submit turn
 * @author Jonathan Baxley
 * @author Andre DeCosta
 * @author Dhaval Shrishrimal
 * @author Joe Netti
 */
public class PostSubmitTurnRoute implements Route {

    private final TemplateEngine templateEngine;

    //Default Constructor
    public PostSubmitTurnRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }


    /**
     * Handles submit a turn request. Either rejects or accepts a move
     * @param request http request
     * @param response http response
     * @return jsonmessage Object
     */
    @Override
    public Object handle(Request request, Response response){
        final Session httpSession = request.session();
        Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute("Player"));
        if (game != null){
            Move move = game.getTurn().getPrevMove();
            Gson gson = new Gson();
            Message message;
            if (game.endTurn()) {
               message = Message.info("true");
               String move_json = gson.toJson(message);
               response.body(move_json);
             return move_json;
            } else {
                message = Message.error("Jump moves possible.");
            }
            String move_json = gson.toJson(message);
            response.body(move_json);
            return move_json;
        }
        else{
            return null;
        }
    }
}
