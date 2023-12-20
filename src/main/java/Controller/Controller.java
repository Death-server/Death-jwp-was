package Controller;

import HttpRequest.HttpRequest;
import HttpResponse.HttpResponse;

public interface Controller {
    void execute(HttpRequest httpRequest, HttpResponse httpResponse);
}
