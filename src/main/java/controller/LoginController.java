package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

import java.io.IOException;

public class LoginController extends AbstractController{
    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return;
    }
    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.forward(httpRequest.getPath());
    }
}
