package com.webcheckers.ui;

import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Row;

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
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetGameRoute implements Route {
  public enum ViewMode { PLAY, SPECTATOR, REPLAY }

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;

  private ViewMode viewMode;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetGameRoute(final TemplateEngine templateEngine, ViewMode viewMode) {
    this.viewMode = viewMode;
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetGameRoute is invoked.");
    final Session httpSession = request.session();
    //
    Map<String, Object> vm = new HashMap<>();
    Player player = httpSession.attribute("Player");
    Game game = Application.playerLobby.getGameByPlayer(httpSession.attribute("Player"));
    vm.put("title", "Game page!");
    vm.put("message", WELCOME_MSG);
    vm.put("currentUser", player);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("viewMode", Game.ViewMode.PLAY);
    vm.put("board", game.getBoardView());
    vm.put("activeColor", game.getActiveColor());
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }

}
