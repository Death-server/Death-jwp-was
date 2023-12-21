package Controller;

import HttpRequest.HttpRequest;
import HttpResponse.HttpResponse;
import util.IOUtils;

public class ForwardController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.addContext(IOUtils.getFileText(httpRequest.getUrl()));
    }
}
