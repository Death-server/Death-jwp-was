package controller;

import request.HttpRequest;
import response.HttpResponse;

public interface Controller {
    void execute(HttpRequest httpRequest, HttpResponse httpResponse);
}
