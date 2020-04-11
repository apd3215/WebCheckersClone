package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Application;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.util.Message;
import spark.*;

import static com.webcheckers.ui.WebServer.GAME_URL;
import static com.webcheckers.ui.WebServer.HOME_URL;
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
        Gson gson = new Gson();

        if (game != null){
            game.isGameOver();
            Move move = game.getTurn().getPrevMove();
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
            Message message = Message.info("true");
            String jsonMessage = gson.toJson(message);
            response.body(jsonMessage);
            get(GAME_URL, new GetGameRoute(templateEngine)); //Home route (default)
            return jsonMessage;        }
    }
}
