package request;

import com.google.common.base.Strings;
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

    public String getQueryValue(String key) {
        String queryValue = requestHeader.getQueryValue(key);

        if(Strings.isNullOrEmpty(queryValue)) {
            final String INVALID_QUERY_EXCEPTION = "맞지 않는 쿼리입니다";
            throw new IllegalArgumentException(INVALID_QUERY_EXCEPTION);
        }

        return queryValue;
    }

}
