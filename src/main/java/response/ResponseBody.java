package response;

public class ResponseBody {

    private String context;

    public ResponseBody() {

    }


    public void setContext(String context) {
        final String NO_CONTEXT_EXCEPTION = "데이터가 없음";
        if(context == null) {
            throw new RuntimeException(NO_CONTEXT_EXCEPTION);
        }
        this.context = context;
    }

    public String getContext() {
        return this.context;
    }
}
