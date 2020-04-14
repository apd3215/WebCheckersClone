package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.Session;
import com.google.gson.Gson;

import static spark.Spark.get;

public class PostResignRoute implements Route{

    private TemplateEngine templateEngine;

    public PostResignRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response){
        final Session httpSession = request.session();

        Gson gson = new Gson();
        Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute("Player"));

        if (game != null) {
            game.setIsResigned(httpSession.attribute("Player"));
            game.gameOver();
        }

        Message message = Message.info("Successful");
        String jsonMessage = gson.toJson(message);
        response.body(jsonMessage);

        httpSession.attribute("ID", -1);
        //Application.gameCenter.endGame(Application.gameCenter.getGameByPlayer(httpSession.attribute("Player")));
        //get(WebServer.HOME_URL, new GetHomeRoute(templateEngine)); //Home route (default)
        return jsonMessage;

    }
}
