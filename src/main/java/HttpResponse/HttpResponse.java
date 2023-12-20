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
}
