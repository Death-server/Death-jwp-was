package HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseSender {
    private static final Logger log = LoggerFactory.getLogger(ResponseSender.class);

    private static DataOutputStream dos;

    public static synchronized void send(DataOutputStream dataOutputStream, HttpResponse httpResponse) throws IOException {
        dos = dataOutputStream;
        forward(httpResponse);
        dos.flush();
    }
    public static void forward(HttpResponse httpResponse) {

        int bodySize = httpResponse.getBodySize();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: " + bodySize + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(httpResponse.getBodyContext().getBytes(), 0, bodySize);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
