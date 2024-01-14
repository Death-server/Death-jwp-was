package response;

import util.IOUtils;

public class HttpResponse {

    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;
    private HttpStatus httpStatus;


    private HttpResponse(ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public static HttpResponse of() {
        return new HttpResponse(new ResponseHeader(), new ResponseBody());
    }
    public void forward(String url) {
        this.responseHeader.setUrl(url);
        this.responseBody.setBody(IOUtils.getFileText(url));
        this.httpStatus = HttpStatus.OK_200;
    }

    public void redirect(String url) {
        this.responseHeader.setUrl(url);
        this.httpStatus = HttpStatus.REDIRECT_302;
    }

    public byte[] getBodyContext() {
        return responseBody.getBody();
    }

    public int getBodySize() {
        return responseBody.getBodyLength();
    }

    public String getHeader() {
        final String NO_HEADER_EXCEPTION = "적절한 헤더 없음";

        if(this.httpStatus == HttpStatus.OK_200) {
            return responseHeader.get200Header(responseBody.getBodyLength());
        } else if(this.httpStatus == HttpStatus.REDIRECT_302) {
            return responseHeader.get302Header();
        }

        throw new RuntimeException(NO_HEADER_EXCEPTION);
    }

    public void addCookie(String key, String value) {
        this.responseHeader.addCookie(key, value);
    }


}
