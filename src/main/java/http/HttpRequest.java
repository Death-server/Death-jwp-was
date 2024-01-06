package http;

import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private String httpVersion;
    private Map<String, String> headers;
    private Map<String, String> parameters;

    public HttpRequest(InputStream in) throws IOException {
        headers = new HashMap<>();
        parameters = new HashMap<>();
        parse(new BufferedReader(new InputStreamReader(in)));
    }

    private void parse(BufferedReader br) throws IOException {
        parseRequestLine(br);
        parseHeaders(br);
        parseBody(br);
    }

    private void parseBody(BufferedReader br) throws IOException {
        //RequestBody
        String contentLength = headers.getOrDefault("Content-Length", null);
        if (contentLength != null) {
            String responseBody = IOUtils.readData(br, Integer.parseInt(contentLength));
            String[] splitResponseBody = responseBody.split("&");
            Arrays.stream(splitResponseBody).forEach(
                    item -> {
                        String[] keyVal = item.split("=", 2);
                        parameters.put(keyVal[0], keyVal[1]);
                    }
            );
        }
    }

    private void parseHeaders(BufferedReader br) throws IOException {
        String line = null;
        while (!(line = br.readLine()).equals("")) {
            String[] headerKeyVal = line.split(": ");
            headers.put(headerKeyVal[0], headerKeyVal[1]);
        }
    }

    private void parseRequestLine(BufferedReader br) throws IOException {
        String requestLine = br.readLine();
        String[] splitRequestLine = requestLine.split(" ");
        method = splitRequestLine[0];
        path = splitRequestLine[1];
        httpVersion = splitRequestLine[2];

        //QueryString
        if (path.contains("?")) {
            String[] pathQueryString = path.split("\\?");
            path = pathQueryString[0];
            String queryStrings = pathQueryString[1];
            parameters.putAll(HttpRequestUtils.parseQueryString(queryStrings));
        }
    }

    public String getMethod(){
        return this.method;
    }

    public String getPath(){
        return this.path;
    }

    public String getHeader(String key) {
        return this.headers.getOrDefault(key, null);
    }

    public String getParameter(String key) {
        return this.parameters.getOrDefault(key, null);
    }
}
