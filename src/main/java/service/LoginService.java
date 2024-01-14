package service;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import session.HttpSession;

import java.util.Objects;

public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    public boolean login(HttpRequest httpRequest) {
        String userId = httpRequest.getData("userId");
        String password = httpRequest.getData("password");

        User user = DataBase.findUserById(userId);

        if(Objects.isNull(user)) {
            return false;
        }

        HttpSession session = httpRequest.getSession();
        session.setAttribute("user", user);

        return user.getPassword().equals(password);
    }
}
