package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import java.security.InvalidParameterException;
import java.util.Map;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);
    private final String version;
    private final HttpMethod method;
    private final String url;
    private Map<String, String> queries;


    public RequestHeader(String header) {
        logger.debug("RequestHeader Constructor");


        String[] parsedHeaders = HttpRequestUtils.parseRequestFirstLine(header);

        final int PARSED_METHOD = 0;
        final int PARSED_VERSION = 1;
        final int PARSED_URL = 2;
        final int QUERY = 3;

        if(header.contains("?")) {
            this.queries = HttpRequestUtils.parseQueryString(parsedHeaders[QUERY]);
        }

        this.method = validMethod(parsedHeaders[PARSED_METHOD]);
        this.version = parsedHeaders[PARSED_VERSION];
        this.url = parsedHeaders[PARSED_URL];
    }

    private HttpMethod validMethod(String method) {
        final String NO_METHOD_EXCEPTION = "처리할 수 있는 메서드가 없음";

        if(method.equals(HttpMethod.GET.getMethod())) {
            return HttpMethod.GET;
        }

        throw new InvalidParameterException(NO_METHOD_EXCEPTION);
    }


    public String getUrl() {
        return url;
    }

    public String getQueryValue(String key) {
        return queries.get(key);
    }

}
