package service;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class SignupService {
    private static final Logger logger = LoggerFactory.getLogger(SignupService.class);
    public void save(User user) {
        logger.debug("[signup] " + user.toString());
        if(Objects.nonNull(DataBase.findUserById(user.getUserId()))) {
            String DUPLICATE_USER_EXCEPTION = "이미 있는 유저임";
            throw new IllegalArgumentException(DUPLICATE_USER_EXCEPTION);
        }

        DataBase.addUser(user);
    }
}
