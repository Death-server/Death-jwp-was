package controller;

import request.HttpRequest;
import response.HttpResponse;
import response.HttpStatus;
import service.SignupService;
import model.User;
import util.IOUtils;

import java.io.IOException;

public class SignupController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = createUser(httpRequest);

        SignupService signupService = new SignupService();
        signupService.save(user);

        httpResponse.redirect("/index.html");
    }

    private User createUser(HttpRequest httpRequest)  {
        final String USERID_KEY = "userId";
        final String PASSWORD_KEY = "password";
        final String NAME_KEY = "name";
        final String EMAIL_KEY = "email";

        return new User(httpRequest.getData(USERID_KEY),
                httpRequest.getData(PASSWORD_KEY),
                httpRequest.getData(NAME_KEY),
                httpRequest.getData(EMAIL_KEY));
    }

}
