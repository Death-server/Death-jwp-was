package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final static Logger logger = LoggerFactory.getLogger(RequestBody.class);

    private Map<String, String> requestBodies;
    public RequestBody() {
        this.requestBodies = new HashMap<>();
        logger.debug("RequestBody Constructor");
    }

    public RequestBody(String body) {
        this.requestBodies = HttpRequestUtils.parseQueryString(body);
    }

    public String getBody(String key) {
        return requestBodies.get(key);
    }
}
