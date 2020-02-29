package com.webcheckers.ui;

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
    //System.out.println(request.queryParams("otherPlayer"));
    final Session httpSession = request.session();
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Game page!");
    vm.put("message", WELCOME_MSG);
    vm.put("gameID", httpSession.attribute("Game"));
    vm.put("currentUser", httpSession.attribute("Player"));
    //vm.put("modeOptionsAsJSON", ); //TODO: implement in next sprint
    //vm.put("redPlayer", );
    //vm.put("whitePlayer", );
    vm.put("viewMode", viewMode);

    BoardView board = new BoardView();
    vm.put("board", board);

    // render the View
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }

  private Row makeRow(int index) {
    Row row = new Row(index);
    return row;
  }


}
