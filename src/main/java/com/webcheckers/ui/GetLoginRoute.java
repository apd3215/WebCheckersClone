package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class GetLoginRoute implements Route {
    
    private final TemplateEngine templateEngine;


    public GetLoginRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required!");
    }

    static final String TITLE_ATTR = "title";
    static final String TITLE = "Sign In";

    
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}
