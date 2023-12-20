package Handler;

import Controller.*;
import HttpRequest.HttpRequest;

import java.net.HttpCookie;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class HandlerMapping {

    private static final Map<String, Controller> controllers = new ConcurrentHashMap<>();

    private static final HandlerMapping handlerMapping = new HandlerMapping();

    private HandlerMapping() {
        init();
    }

    private static void init() {
        controllers.put("/index.html", new HomeController());

    }

    public static HandlerMapping of() {
        return handlerMapping;
    }

    public Controller getController(HttpRequest httpRequest) {
        Controller controller = controllers.get(httpRequest.getUrl());

        if(controller == null) {
            return new HelloWorldController();
        }
        return controller;
    }
}
