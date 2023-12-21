package Handler;


import HttpRequest.HttpRequest;
import com.google.common.collect.Maps;
import Controller.*;
import java.util.Map;

public class HandlerMapper {

    private static final Map<String, Controller> controllers = Maps.newHashMap();
    private static final HandlerMapper HANDLER_MAPPER = new HandlerMapper();
    private static final ForwardController forwardController = new ForwardController();

    private HandlerMapper() {
        init();
    }

    private static void init() {
        controllers.put("/", new HomeController());
        controllers.put("/user/create", new SignupController());
    }

    public static HandlerMapper of() {
        return HANDLER_MAPPER;
    }

    public Controller getController(HttpRequest httpRequest) {
        Controller controller = controllers.get(httpRequest.getUrl());

        if(controller == null) {
            return forwardController;
        }
        return controller;
    }
}
