package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);
    private HttpMethod method;
    private String path;
    private String httpVersion;
    private Map<String, String> params = new HashMap<>();

    public RequestLine(String requestLine){
        log.debug("request Line : {}", requestLine);
        String[] tokens = requestLine.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException(requestLine + "이 형식에 맞지 않습니다.");
        }
        String[] splitRequestLine = requestLine.split(" ");
        method = HttpMethod.valueOf(splitRequestLine[0]);
        path = splitRequestLine[1];
        httpVersion = splitRequestLine[2];

        //QueryString
        if (path.contains("?")) {
            String[] pathQueryString = path.split("\\?");
            path = pathQueryString[0];
            String queryStrings = pathQueryString[1];
            params = HttpRequestUtils.parseQueryString(queryStrings);
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
