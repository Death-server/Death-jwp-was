package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseSender {
    private static final Logger log = LoggerFactory.getLogger(ResponseSender.class);

    private static DataOutputStream dos;
    private static final String HEADER_BODY_SEPARATOR = "\r\n";


    public static synchronized void send(DataOutputStream dataOutputStream, HttpResponse httpResponse) throws IOException {
        dos = dataOutputStream;
        forward(httpResponse);
        dos.flush();
    }
    public static void forward(HttpResponse httpResponse) {

        int bodySize = httpResponse.getBodySize();

        try {
            dos.writeBytes(httpResponse.getHeader(HttpStatus.OK_200));
            dos.writeBytes(HEADER_BODY_SEPARATOR);
            dos.write(httpResponse.getBodyContext().getBytes(), 0, bodySize);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
