package HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Parser;

import java.security.InvalidParameterException;

public class RequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);
    private final String version;
    private final HttpMethod method;
    private final String url;


    public RequestHeader(String header) {
        logger.debug("RequestHeader Constructor");
        String[] parsedHeaders = Parser.parseRequestHeader(header);

        final int PARSED_VERSION = 2;
        final int PARSED_METHOD = 0;
        final int PARSED_URL = 1;

        this.version = parsedHeaders[PARSED_VERSION];
        this.method = separateMethod(parsedHeaders[PARSED_METHOD]);
        this.url = parsedHeaders[PARSED_URL];
    }

    private HttpMethod separateMethod(String method) {
        final String NO_METHOD_EXCEPTION = "처리할 수 있는 메서드가 없음";
        if(method.equals(HttpMethod.GET.getMethod())) {
            return HttpMethod.GET;
        }

        throw new InvalidParameterException(NO_METHOD_EXCEPTION);
    }


    public String getUrl() {
        return url;
    }
}
