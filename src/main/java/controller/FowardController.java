package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public class FowardController extends AbstractController{
    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.forward(httpRequest.getPath());
    }
}
