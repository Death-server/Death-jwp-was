package HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private HttpRequest(RequestHeader requestHeader, RequestBody requestBody) {
        logger.debug("Request Constructor");
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(String header) {
        return new HttpRequest(new RequestHeader(header), new RequestBody());
    }



    public String getUrl() {
        return requestHeader.getUrl();
    }

    public String getFileText() {
        final String BASE_URL = "webapp";
        try {
            File file = new File(BASE_URL + requestHeader.getUrl());
            if(file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                return IOUtils.readData(br, (int) file.length());
            }
        } catch(IOException ioException) {
            // 404 Not Found
            logger.error(ioException.getMessage());
        }
        return null;
    }
}
