package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.Application;
import spark.*;

import com.webcheckers.util.Message;

import static com.webcheckers.ui.WebServer.*;
import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;


  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetHomeRoute is initialized.");
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
    final Session httpSession = request.session();

    LOG.finer("GetHomeRoute is invoked.");
    //
    if(httpSession.attribute("Player") == null) {
      Map<String, Object> vm = new HashMap<>();
      vm.put("title", "Welcome!");
      // display a user message in the Home page
      vm.put("message", WELCOME_MSG);
      vm.put("num", Application.playerLobby.getNum_logged_in());
      // render the View
      return templateEngine.render(new ModelAndView(vm, "home.ftl"));
    }
    else{
      Map<String, Object> vm = new HashMap<>();
      vm.put("title", "Welcome");
      vm.put("message", WELCOME_MSG);
      vm.put("currentUser", httpSession.attribute("Player"));
      vm.put("signed", Application.playerLobby.get_logged_names());
      return templateEngine.render(new ModelAndView(vm, "home.ftl"));
      //response.redirect(WELCOME_URL);
    }
  }
}
