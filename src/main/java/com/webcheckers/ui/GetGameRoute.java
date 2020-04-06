package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import com.webcheckers.model.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.Session;

import com.webcheckers.util.Message;
import com.google.gson.Gson;

import static com.webcheckers.ui.WebServer.GAME_URL;
import static spark.Spark.get;
import static spark.Spark.redirect;

/**
 * The UI Controller to GET the game route.
 */
public class GetGameRoute implements Route {

  //Constants
  public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  public static final String GAME_TITLE = "Game page!";

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetGameRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Game route.
   *
   * @param request  the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Game page
   */
  @Override
  public Object handle(Request request, Response response) throws InterruptedException{
    LOG.finer("GetGameRoute is invoked.");
    final Session httpSession = request.session();

    Map<String, Object> vm = new HashMap<>();
    
    Player player = httpSession.attribute(SessionAttributes.PLAYER);
    Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute(SessionAttributes.PLAYER));
    Gson gson = new Gson();

    if (game != null) {
      vm.put("title", "Game page!");
      vm.put("currentUser", player);
      vm.put("redPlayer", game.getRedPlayer());
      vm.put("whitePlayer", game.getWhitePlayer());
      vm.put("viewMode", Game.ViewMode.PLAY);
      vm.put("board", game.getBoardView());
      vm.put("activeColor", game.getActiveColor());

      final Map<String, Object> modeOptions = new HashMap<>(2);
      modeOptions.put("isGameOver", true);

      if (game.getIsResigned() == httpSession.attribute(SessionAttributes.PLAYER)){
        modeOptions.put("gameOverMessage", "You resigned. BOO.");
        game.gameOver();
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      } else if (game.getIsResigned() != null){
        modeOptions.put("gameOverMessage", "Other Player resigned. You Win.");
        game.gameOver();
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        Application.gameCenter.endGame(game);
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      }
      if (game.isGameOver()){


        if (player.equals(game.getWinner())){
          modeOptions.put("gameOverMessage", "you win");
        }
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));

      }
      else{
        vm.put("message", WELCOME_MSG);
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      }
    }

    else{
      response.redirect("/");
      return null;
    }
  }
}
