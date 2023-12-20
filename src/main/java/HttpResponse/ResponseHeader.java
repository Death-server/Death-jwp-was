package HttpResponse;

public class ResponseHeader {

    public ResponseHeader() {

    }
    public String get200Header(int bodySize) {
        final String HTTP_VERSION = "HTTP/1.1";
        final String HEADER_CONTENT_SEPARATOR = "\r\n";
        final String STATUS_SEPARATOR = " ";
        final String NAME_VALUE_SEPARATOR = ": ";

        final String CONTENT_TYPE = "Content-Type";
        final String CONTENT_TYPE_SEPARATOR = ";";
        final String HTML = "text/html";
        final String CHARSET = "charset";
        final String CHARSET_SEPARATOR = "=";
        final String UTF_8 = "utf-8";
        final String CONTENT_LENGTH = "Content-Length";

        return HTTP_VERSION + STATUS_SEPARATOR +
                HttpStatus.OK_200.getCode() + STATUS_SEPARATOR +
                HttpStatus.OK_200.getMessage() + STATUS_SEPARATOR +
                HEADER_CONTENT_SEPARATOR +
                CONTENT_TYPE + NAME_VALUE_SEPARATOR +
                HTML + CONTENT_TYPE_SEPARATOR +
                CHARSET + CHARSET_SEPARATOR + UTF_8 +
                HEADER_CONTENT_SEPARATOR +
                CONTENT_LENGTH + NAME_VALUE_SEPARATOR +
                bodySize + HEADER_CONTENT_SEPARATOR;
    }
}
