package response;

import util.MimeType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResponseHeader {

    private final String HTTP_VERSION = "HTTP/1.1";
    private final String HEADER_CONTENT_SEPARATOR = "\r\n";
    private final String STATUS_SEPARATOR = " ";
    private final String NAME_VALUE_SEPARATOR = ": ";

    private String url;
    private final Map<String, String> cookies;
    public ResponseHeader() {
        this.url = "";
        cookies = new HashMap<>();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }


    public String get200Header(int bodySize) {
        final String CONTENT_TYPE = "Content-Type";
        final String CONTENT_TYPE_SEPARATOR = ";";
        final String CHARSET = "charset";
        final String CHARSET_SEPARATOR = "=";
        final String UTF_8 = "utf-8";
        final String CONTENT_LENGTH = "Content-Length";

        return HTTP_VERSION + STATUS_SEPARATOR +
                HttpStatus.OK_200.getCode() + STATUS_SEPARATOR +
                HttpStatus.OK_200.getMessage() + STATUS_SEPARATOR +
                HEADER_CONTENT_SEPARATOR +
                CONTENT_TYPE + NAME_VALUE_SEPARATOR + MimeType.applyMimeHeader(url) +
                CONTENT_TYPE_SEPARATOR +
                CHARSET + CHARSET_SEPARATOR + UTF_8 +
                HEADER_CONTENT_SEPARATOR +
                CONTENT_LENGTH + NAME_VALUE_SEPARATOR +
                bodySize + HEADER_CONTENT_SEPARATOR;
    }

    public String get302Header() {
        final String LOCATION = "Location";

        String header = HTTP_VERSION + STATUS_SEPARATOR +
                HttpStatus.REDIRECT_302.getCode() + STATUS_SEPARATOR +
                HttpStatus.REDIRECT_302.getMessage() + STATUS_SEPARATOR +
                HEADER_CONTENT_SEPARATOR +
                LOCATION + NAME_VALUE_SEPARATOR + url
                + HEADER_CONTENT_SEPARATOR;
        if(!cookies.isEmpty()) {
            header += addCookieHeader();
        }

        return header;

    }

    private String addCookieHeader() {
        final String COOKIE = "Set-Cookie";
        final String COOKIE_DEFAULT_PATH = "path=/";
        final String COOKIE_SEPARATOR = ";";
        final String COOKIE_NAME_VALUE_SEPARATOR = "=";

        StringBuilder cookiesHeader = new StringBuilder();

        cookiesHeader.append(COOKIE + NAME_VALUE_SEPARATOR);

        for (String key : cookies.keySet()) {
            cookiesHeader.append(key).append(COOKIE_NAME_VALUE_SEPARATOR).append(cookies.get(key)).append(COOKIE_SEPARATOR);
        }


        cookiesHeader.append(COOKIE_DEFAULT_PATH);

        return cookiesHeader.toString();
    }
}
