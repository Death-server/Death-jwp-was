package controller;

import request.HttpRequest;
import response.HttpResponse;
import session.HttpSession;

public class ListController implements Controller{
    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        final String LOGIN_HTML = "/user/login.html";
        final String LIST_HTML = "/user/list.html";

        if(isLogin(httpRequest.getSession())) {
            httpResponse.redirect(LIST_HTML);
            return;
        }

        httpResponse.redirect(LOGIN_HTML);

    }

    private static boolean isLogin(HttpSession httpSession) {
        Object user = httpSession.getAttribute("user");
        if(user == null) {
            return false;
        }
        return true;
    }
}
