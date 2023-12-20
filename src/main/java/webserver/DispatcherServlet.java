package webserver;

import Controller.Controller;
import Handler.HandlerMapping;
import HttpRequest.HttpRequest;
import HttpResponse.HttpResponse;
import HttpResponse.ResponseSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

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

            String header = br.readLine();

            HttpRequest httpRequest = HttpRequest.of(header);
            HttpResponse httpResponse = HttpResponse.of();
            HandlerMapping handlerMapping = HandlerMapping.of();

            Controller controller = handlerMapping.getController(httpRequest);
            controller.execute(httpRequest, httpResponse);

            ResponseSender.send(dos, httpResponse);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
