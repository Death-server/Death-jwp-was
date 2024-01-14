package webserver;

import com.google.common.base.Strings;
import controller.Controller;
import request.HttpRequest;
import request.RequestHeader;
import response.HttpResponse;
import response.ResponseSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;


    public RequestHandler(Socket connection) {
        this.connection = connection;
    }


    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String statusLine = br.readLine();
            RequestHeader requestHeader = getHeaders(br);
            HttpRequest httpRequest = getHttpRequest(br, statusLine, requestHeader);
            HttpResponse httpResponse = HttpResponse.of();

            if(httpRequest.getCookie("JSESSIONID") == null) {
                httpResponse.addCookie("JSESSIONID", UUID.randomUUID().toString());
            }

            HandlerMapper handlerMapper = HandlerMapper.of();
            Controller controller = handlerMapper.getController(httpRequest);
            controller.execute(httpRequest, httpResponse);

            ResponseSender.send(dos, httpResponse);
        } catch (IOException | RuntimeException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private static HttpRequest getHttpRequest(BufferedReader br, String statusLine, RequestHeader requestHeader) throws IOException {
        int contentLength = requestHeader.getContentLength();

        if(contentLength != 0) {
            String bodies = IOUtils.readData(br, contentLength);
            return HttpRequest.of(statusLine, requestHeader, bodies);
        }
        return HttpRequest.of(statusLine, requestHeader);
    }

    private static RequestHeader getHeaders(BufferedReader br) throws IOException {
        Map<String, String> map = new HashMap<>();
        String header;

        while(!Strings.isNullOrEmpty(header = br.readLine())) {
            HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(header);
            map.put(pair.getKey(), pair.getValue());
        }
        return new RequestHeader(map);
    }
}
