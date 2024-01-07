package webserver;

import com.google.common.base.Strings;
import controller.Controller;
import request.HttpRequest;
import request.RequestBody;
import response.HttpResponse;
import response.ResponseSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class DispatcherServlet implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private final Socket connection;


    public DispatcherServlet(Socket connection) {
        this.connection = connection;
    }


    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String statusLine = br.readLine();

            int bodySize = 0;
            String header;
            while(!Strings.isNullOrEmpty(header = br.readLine())) {
                String key = HttpRequestUtils.parseHeader(header).getKey();
                String value = HttpRequestUtils.parseHeader(header).getValue();
                log.debug(value);
                if(key.equals("Content-Length")) {
                    bodySize = Integer.parseInt(value);
                }
            }

            if(bodySize != 0) {
                char[] tmp = new char[bodySize];
                br.read(tmp, 0, bodySize);
                String s = "";
                for(int i = 0; i < bodySize; i++) {
                    s += tmp[i];
                }
                log.debug("RequestBody : " + s);
            }

            HttpRequest httpRequest = HttpRequest.of(statusLine);
            HttpResponse httpResponse = HttpResponse.of();
            HandlerMapper handlerMapper = HandlerMapper.of();

            Controller controller = handlerMapper.getController(httpRequest);
            controller.execute(httpRequest, httpResponse);

            ResponseSender.send(dos, httpResponse);
        } catch (IOException | RuntimeException e) {
            log.error(e.getMessage());
        }
    }
}
