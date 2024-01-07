package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

import java.io.IOException;

public class CreateUserController extends AbstractController{
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");
        DataBase.addUser(new User(userId, password, name, email));
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");
        DataBase.addUser(new User(userId, password, name, email));
        httpResponse.forward("/index.html");
    }
}
