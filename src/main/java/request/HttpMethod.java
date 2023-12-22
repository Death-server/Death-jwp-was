package request;

import java.security.InvalidParameterException;

public enum HttpMethod {
    GET("GET"), POST("POST");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static HttpMethod changeStringToMethod(String method) {
        final String NO_METHOD_EXCEPTION = "처리할 수 있는 메서드가 없음";

        if(method.equals(HttpMethod.GET.getMethod())) {
            return HttpMethod.GET;
        } else if(method.equals(HttpMethod.POST.getMethod())) {
            return HttpMethod.POST;
        }

        throw new InvalidParameterException(NO_METHOD_EXCEPTION);
    }
}
