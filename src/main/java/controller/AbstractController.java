package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String method = httpRequest.getMethod();
        if (method.equals("GET")) {
            doGet(httpRequest, httpResponse);
        }

        if (method.equals("POST")) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;

    protected abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
