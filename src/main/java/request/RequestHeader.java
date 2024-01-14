package request;

import session.HttpSession;
import session.HttpSessions;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestHeader {

    private Map<String, String> requestHeaders;
    private Map<String, String> cookies;
    private final String SESSION_ID = "JSESSIONID";


    public RequestHeader(Map<String, String> headers) {
        final String COOKIE = "Cookie";

        this.requestHeaders = headers;
        this.cookies = HttpRequestUtils.parseCookies(headers.get(COOKIE));
    }

    public int getContentLength() {
        final String CONTENT_LENGTH = "Content-Length";
        if(this.requestHeaders.get(CONTENT_LENGTH) == null) {
            return 0;
        }
        return Integer.parseInt(this.requestHeaders.get(CONTENT_LENGTH));
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public String getSessionId() {
        return cookies.get(SESSION_ID);
    }
}
