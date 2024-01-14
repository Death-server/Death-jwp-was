package controller;

import request.HttpRequest;
import response.HttpResponse;
import response.HttpStatus;
import util.IOUtils;

public class ForwardController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getUrl());
    }
}
