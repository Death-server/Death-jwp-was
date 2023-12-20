package Controller;

import HttpRequest.HttpRequest;
import HttpResponse.HttpResponse;

public class HomeController implements Controller{

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.addContext(httpRequest.getFileText());
    }
}
