package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

import java.io.IOException;

public class LoginFormController extends AbstractController {
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        System.out.println("LoginFormController 진입");
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        if (!areValidParamsForLogin(userId, password)) {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
        }

        User findUser = DataBase.findUserById(userId);
        if (findUser == null) {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
        httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

    }

    private static boolean areValidParamsForLogin(String userId, String password) {
        return userId != null && password != null;

    }

}
