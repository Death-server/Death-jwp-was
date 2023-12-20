package HttpResponse;

public enum HttpStatus {
    OK_200(200, "OK"), BAD_REQUEST_400(400, "BAD REQUEST"), REDIRECT_302(302, "FOUND");

    private final int code;
    private final String message;

    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
