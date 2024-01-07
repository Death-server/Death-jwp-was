package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public class IndexController extends AbstractController{

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        return;
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.forward("/index.html");
    }
}
