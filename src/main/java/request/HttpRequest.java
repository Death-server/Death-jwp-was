package request;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.HttpSession;
import session.HttpSessions;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final StatusLine statusLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private HttpRequest(StatusLine statusLine, RequestHeader requestHeader, RequestBody requestBody) {
        logger.debug("Request Constructor");
        this.statusLine = statusLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(String requestLine, RequestHeader requestHeader, String bodies) {
        return new HttpRequest(new StatusLine(requestLine), requestHeader, new RequestBody(bodies));
    }

    public static HttpRequest of(String requestLine, RequestHeader requestHeader) {
        return new HttpRequest(new StatusLine(requestLine), requestHeader, null);
    }


    public String getUrl() {
        return statusLine.getUrl();
    }

    public String getCookie(String key) {
        return requestHeader.getCookie(key);
    }

    public HttpSession getSession() {
        return HttpSessions.getSession(requestHeader.getSessionId());
    }

    public String getData(String key)  {

        String value = null;

        if(statusLine.isGet()) {
            value = statusLine.getQueryValue(key);
        } else if(statusLine.isPost()) {
            value = requestBody.getBody(key);
        }

        if(Strings.isNullOrEmpty(value)) {
            final String INVALID_QUERY_EXCEPTION = "맞지 않는 쿼리임";
            throw new IllegalArgumentException(INVALID_QUERY_EXCEPTION);
        }

        return value;
    }

}
