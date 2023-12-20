package HttpResponse;

public class ResponseBody {

    private String context;

    public ResponseBody() {
    }


    public void setContext(String context) {
        if(context == null) {
            throw new RuntimeException("데이터가 안 읽힘");
        }
        this.context = context;
    }

    public String getContext() {
        return this.context;
    }
}
