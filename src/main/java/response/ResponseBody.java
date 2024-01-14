package response;

public class ResponseBody {

    private byte[] body;


    public ResponseBody() {
    }


    public void setBody(byte[] body) {
        String NO_CONTEXT_EXCEPTION = "데이터가 없음";

        if(body == null) {
            throw new RuntimeException(NO_CONTEXT_EXCEPTION);
        }
        this.body = body;
    }

    public byte[] getBody() {
        return this.body;
    }

    public int getBodyLength() {
        if(this.body == null) {
            return 0;
        }
        return this.body.length;
    }
}
