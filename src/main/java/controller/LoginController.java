package controller;

import model.User;
import request.HttpRequest;
import response.HttpResponse;
import service.LoginService;
import session.HttpSession;

public class LoginController implements Controller{

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {

        final String USERID_KEY = "userId";
        final String PASSWORD_KEY = "password";
        final String LOGIN_KEY = "logined";
        final String LOGIN_SUCCESS = "True";
        final String LOGIN_FAILED = "False";

        LoginService loginService = new LoginService();

        boolean isLogin = loginService.login(httpRequest);

        if(!isLogin) {
            httpResponse.addCookie(LOGIN_KEY, LOGIN_FAILED);
            httpResponse.redirect("/user/login_failed.html");
            return;
        }
        httpResponse.redirect("/index.html");
    }
}
