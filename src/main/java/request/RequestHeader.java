package request;

import com.google.common.collect.Maps;
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
    private Map<String, String> queries = Maps.newHashMap();


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

        this.method = HttpMethod.changeStringToMethod(parsedHeaders[PARSED_METHOD]);
        this.version = parsedHeaders[PARSED_VERSION];
        this.url = parsedHeaders[PARSED_URL];
    }

    public String getUrl() {
        return url;
    }

    public String getQueryValue(String key) {
        return queries.get(key);
    }

}
