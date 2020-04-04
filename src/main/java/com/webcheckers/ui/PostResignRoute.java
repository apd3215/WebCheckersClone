package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.GameCenter;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.Session;


import static spark.Spark.get;



public class PostResignRoute implements Route{

    private TemplateEngine templateEngine;
    public static final String HOME_URL = "/";


    public PostResignRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response){
        final Session httpSession = request.session();

        httpSession.attribute("resign", true);
        String success = "Successful";
        response.body(success);
        Application.gameCenter.endGame(Application.gameCenter.getGameByPlayer(httpSession.attribute("player")));
        get(HOME_URL, new GetHomeRoute(templateEngine)); //Home route (default)

        return success;

    }
}
