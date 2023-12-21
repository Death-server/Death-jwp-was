package Controller;

import HttpRequest.HttpRequest;
import HttpResponse.HttpResponse;
import util.IOUtils;

public class HomeController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        final String HOME = "/index.html";

        httpResponse.addContext(IOUtils.getFileText(HOME));
    }
}
