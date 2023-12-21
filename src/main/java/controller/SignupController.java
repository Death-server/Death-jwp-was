package controller;

import request.HttpRequest;
import response.HttpResponse;
import service.SignupService;
import model.User;
import util.IOUtils;

public class SignupController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = createUser(httpRequest);

        SignupService.save(user);

        httpResponse.addContext(IOUtils.getFileText("/index.html"));
    }

    private User createUser(HttpRequest httpRequest) {
        final String USERID_KEY = "userId";
        final String PASSWORD_KEY = "password";
        final String NAME_KEY = "name";
        final String EMAIL_KEY = "email";

        return new User(httpRequest.getQueryValue(USERID_KEY),
                httpRequest.getQueryValue(PASSWORD_KEY),
                httpRequest.getQueryValue(NAME_KEY),
                httpRequest.getQueryValue(EMAIL_KEY));
    }

}
