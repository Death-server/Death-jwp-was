package Handler;


import HttpRequest.HttpRequest;
import com.google.common.collect.Maps;
import Controller.*;
import java.util.Map;

public class HandlerMapping {

    private static final Map<String, Controller> controllers = Maps.newHashMap();
    private static final HandlerMapping handlerMapping = new HandlerMapping();
    private static final ForwardController forwardController = new ForwardController();

    private HandlerMapping() {
        init();
    }

    private static void init() {
        controllers.put("/", new HomeController());
        controllers.put("/user/create", new SignupController());
    }

    public static HandlerMapping of() {
        return handlerMapping;
    }

    public Controller getController(HttpRequest httpRequest) {
        Controller controller = controllers.get(httpRequest.getUrl());

        if(controller == null) {
            return forwardController;
        }
        return controller;
    }
}
