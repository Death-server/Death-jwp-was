package controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private Map<String, Controller> controllers;

    public RequestMapping() {
        controllers = new HashMap<>();
        controllers.put("static", new FowardController());
        controllers.put("/index.html", new FowardController());
        controllers.put("/user/form.html", new FowardController());
        controllers.put("/user/login.html", new FowardController());

        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginFormController());
        controllers.put("/user/list", new ListUserController());
    }
    public Controller getController(String path){
        if (path.contains(".css") || path.contains(".js") || path.contains(".ico")) {
            return controllers.get("static");
        }
        return controllers.getOrDefault(path,null);
    }
}
