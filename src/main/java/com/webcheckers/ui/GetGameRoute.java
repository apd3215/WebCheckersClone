package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.Player;
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
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetGameRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Game route.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Game page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetGameRoute is invoked.");
    final Session httpSession = request.session();

    Map<String, Object> vm = new HashMap<>();
    Player player = httpSession.attribute(SessionAttributes.PLAYER);
    httpSession.attribute(SessionAttributes.RESIGN, "false");
    Game game = Application.gameCenter.getGameByPlayer(httpSession.attribute(SessionAttributes.PLAYER));

    vm.put(VMAttributes.TITLE, GAME_TITLE);
    vm.put(VMAttributes.MESSAGE, WELCOME_MSG);
    vm.put(VMAttributes.CURRENT_USER, player);
    vm.put(VMAttributes.RED_PLAYER, game.getRedPlayer());
    vm.put(VMAttributes.WHITE_PLAYER, game.getWhitePlayer());
    vm.put(VMAttributes.VIEW_MODE, Game.ViewMode.PLAY);
    vm.put(VMAttributes.BOARD, game.getBoardView());
    vm.put(VMAttributes.ACTIVE_COLOR, game.getActiveColor());

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }

}
