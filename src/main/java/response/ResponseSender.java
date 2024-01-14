package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseSender {
    private static final Logger log = LoggerFactory.getLogger(ResponseSender.class);

    private static final String HEADER_BODY_SEPARATOR = "\r\n";


    public static void send(DataOutputStream dataOutputStream, HttpResponse httpResponse) throws IOException {
        int bodySize = httpResponse.getBodySize();

        try {
            dataOutputStream.writeBytes(httpResponse.getHeader());
            dataOutputStream.writeBytes(HEADER_BODY_SEPARATOR);
            if(bodySize != 0) {
                dataOutputStream.write(httpResponse.getBodyContext(), 0, bodySize);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        dataOutputStream.flush();
        dataOutputStream.close();
    }
}
