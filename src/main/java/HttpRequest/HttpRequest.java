package HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


}
