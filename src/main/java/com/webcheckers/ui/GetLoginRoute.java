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
    
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
}