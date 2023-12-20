package Controller;

import HttpRequest.HttpRequest;
import HttpResponse.HttpResponse;

public class HelloWorldController implements Controller{

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.addContext(" Hello World  ");
    }
}
