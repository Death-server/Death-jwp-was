package HttpResponse;

public class HttpResponse {

    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;


    private HttpResponse(ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public static HttpResponse of() {
        return new HttpResponse(new ResponseHeader(), new ResponseBody());
    }
    public void addContext(String text) {
        responseBody.setContext(text);
    }

    public String getBodyContext() {
        return responseBody.getContext();
    }

    public int getBodySize() {
        return responseBody.getContext().length();
    }

    public String getHeader(HttpStatus httpStatus) {
        final String NO_HEADER_EXCEPTION = "적절한 헤더 없음";

        if(httpStatus == HttpStatus.OK_200) {
            return responseHeader.get200Header(responseBody.getContext().length());
        }
        throw new RuntimeException(NO_HEADER_EXCEPTION);
    }
}
