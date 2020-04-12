package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;
import spark.Session;

import com.webcheckers.util.Message;
import com.google.gson.Gson;

/**
 * The UI Controller to GET the game route.
 */
public class GetSpectatorGameRoute implements Route {

  //Constants
  public static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  public static final String GAME_TITLE = "Game page!";

  private static final Logger LOG = Logger.getLogger(GetSpectatorGameRoute.class.getName());
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetSpectatorGameRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetSpectatorGameRoute is initialized.");
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
    LOG.finer("GetSpectatorGameRoute is invoked.");
    final Session httpSession = request.session();

    Map<String, Object> vm = new HashMap<>();
    Player playing = null;
    if (httpSession.attribute("isFirst") == null){
      String playingStr = request.queryParams("otherPlayer");
      playing = Application.playerLobby.getPlayers().get(playingStr);
      httpSession.attribute("Playing", playing);
      httpSession.attribute("isFirst", false);
    } else {
      playing = httpSession.attribute("Playing");
    }
      Player spectator = httpSession.attribute(SessionAttributes.PLAYER);

    Game game = Application.gameCenter.getGameByPlayer(playing);


    Gson gson = new Gson();

    if (game != null) {
      vm.put("title", "Game page!");
      vm.put("currentUser", spectator);
      vm.put("redPlayer", game.getRedPlayer());
      vm.put("whitePlayer", game.getWhitePlayer());
      vm.put("viewMode", Game.ViewMode.SPECTATOR);
      vm.put("board", game.getBoardView());
      vm.put("activeColor", game.getActiveColor());

      httpSession.attribute("activeColor", game.getActiveColor());

      final Map<String, Object> modeOptions = new HashMap<>(2);
      modeOptions.put("isGameOver", true);

      //TODO: Change logic
      if (game.getIsResigned() == playing){
        modeOptions.put("gameOverMessage", playing.getName() + "resigned. BOO.");
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      } else if (game.getIsResigned() != null){
        modeOptions.put("gameOverMessage", "Other Player resigned. You Win.");
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      } if (game.getIsGameOver()){
          if (playing.equals(game.getWinner())) { //TODO: Fix (current player -> spectator)
            modeOptions.put("gameOverMessage", playing.getName() + " wins");
          }
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      } else {
        vm.put("message", WELCOME_MSG);
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
      }
    } else {
      response.redirect("/");
      return null;
    }
  }
}
