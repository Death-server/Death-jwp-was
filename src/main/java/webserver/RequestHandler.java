package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String requestLine = null;
            String requestMethod = null;
            String path = null;
            String httpVersion;
            String responseBody;
            Map<String, String> requestHeaders = new HashMap<>();
            Map<String, String> requestParam = new HashMap<>();
            DataOutputStream dos = new DataOutputStream(out);

            if (br.ready()) {
                //RequestLine Parsing
                requestLine = br.readLine();
                log.info("requestLine: " + requestLine);
                String[] splitRequestLine = requestLine.split(" ");
                requestMethod = splitRequestLine[0];
                path = splitRequestLine[1];
//                httpVersion = splitRequestLine[2];

                String line = null;
                while (!(line = br.readLine()).equals("")) {
                    String[] headerKeyVal = line.split(": ");
                    requestHeaders.put(headerKeyVal[0], headerKeyVal[1]);
                }

                //QueryString
                if (path.contains("?")) {
                    String[] pathQueryString = path.split("\\?");
                    path = pathQueryString[0];
                    String queryStrings = pathQueryString[1];
                    requestParam.putAll(HttpRequestUtils.parseQueryString(queryStrings));
                }

                //RequestBody
                String contentLength = requestHeaders.getOrDefault("Content-Length", null);
                if (contentLength != null) {
                    responseBody = IOUtils.readData(br, Integer.parseInt(contentLength));
                    String[] splitResponseBody = responseBody.split("&");
                    Arrays.stream(splitResponseBody).forEach(
                            item -> {
                                String[] keyVal = item.split("=", 2);
                                requestParam.put(keyVal[0], keyVal[1]);
                            }
                    );
                }

                //Step1
                if (requestMethod.equals("GET") && (path.equals("/index.html") || path.equals("/"))) {
                    response200(dos, "/index.html");
                }

                //Step2
                if (requestMethod.equals("GET") && path.equals("/user/form.html")) {
                    response200(dos, path);
                }

                //Step3
                if (requestMethod.equals("GET") && path.equals("/user/create")) {
                    String userId = requestParam.get("userId");
                    String password = requestParam.get("password");
                    String name = requestParam.get("name");
                    String email = requestParam.get("email");
                    DataBase.addUser(new User(userId, password, name, email));

                    response200(dos, "/index.html");
                }

                //Step4
                if (requestMethod.equals("POST") && path.equals("/user/create")) {
                    String userId = requestParam.getOrDefault("userId", null);
                    String password = requestParam.getOrDefault("password", null);
                    String name = requestParam.getOrDefault("name", null);
                    String email = requestParam.getOrDefault("email", null);

                    if (areValidParamsForCreate(userId, password, name, email)) {
                        DataBase.addUser(new User(userId, password, name, email));
                        response302(dos, "/index.html");
                    }
                    //TODO : 회원가입 에러
                }

                //Step5
                if (requestMethod.equals("GET") && path.equals("/user/login.html")) {
                    response200(dos, path);
                }

                if (requestMethod.equals("POST") && path.equals("/user/login")) {
                    String userId = requestParam.getOrDefault("userId", null);
                    String password = requestParam.getOrDefault("password", null);
                    if (!areValidParamsForLogin(userId, password)) {
                        responseLogin(dos, "/user/login_failed.html", "logined=false; Path=/");
                    }

                    User findUser = DataBase.findUserById(userId);
                    if (findUser == null) {
                        responseLogin(dos, "/user/login_failed.html", "logined=false; Path=/");
                    }

                    responseLogin(dos, "/index.html", "logined=true; Path=/");
                }

                //Step6
                if (requestMethod.equals("GET") && path.equals("/user/list")) {
                    String cookie = requestHeaders.getOrDefault("Cookie", null);
                    if (cookie == null) {
                        response200(dos, "/user/login.html");
                    }

                    Map<String, String> cookies = HttpRequestUtils.parseCookies(cookie);
                    String logined = cookies.getOrDefault("logined", null);
                    if (logined == null || Boolean.parseBoolean(logined)) {
                        response200(dos, "/user/login.html");
                    }
                    Collection<User> users = DataBase.findAll();

                    StringBuilder sb = new StringBuilder();
                    sb.append("<table border='1'>");
                    for (User user : users) {
                        sb.append("<tr>")
                                .append("<td>" + user.getUserId() + "</td>")
                                .append("<td>" + user.getName() + "</td>")
                                .append("<td>" + user.getEmail() + "</td>")
                                .append("</tr>");
                    }
                    sb.append("</table>");
                    byte[] body = sb.toString().getBytes();
                    response200Header(dos,body.length);
                    responseBody(dos, body);
                }

                //Step7
                if (requestMethod.equals("GET") && (path.contains(".css") || path.contains(".js") || path.contains(".ico"))) {
                    response200MIME(dos, path);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static boolean areValidParamsForLogin(String userId, String password) {
        return userId != null && password != null;
    }

    private static boolean areValidParamsForCreate(String userId, String password, String name, String email) {
        return userId != null && password != null && name != null && email != null;
    }

    private void response302(DataOutputStream dos, String path) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + path).toPath());
        response302Header(dos, body.length, "http://localhost:8080/index.html");
        responseBody(dos, body);
    }

    private void response200MIME(DataOutputStream dos, String path) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + path).toPath());
        String contentType = null;
        if (path.contains(".css")) {
            contentType = "text/css";
        }
        if (path.contains(".js")) {
            contentType = "text/javascript";
        }
        if (path.contains(".ico")) {
            contentType = "image/vnd.microsoft.icon";
        }
        log.info("contentType: " + contentType);

        response200MIMEHeader(dos, body.length, contentType);
        responseBody(dos, body);
    }

    private void response200(DataOutputStream dos, String path) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + path).toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void responseLogin(DataOutputStream dos, String path, String cookie) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + path).toPath());
        responseLoginHeader(dos, body.length, cookie);
        responseBody(dos, body);
    }

    private void responseLoginHeader(DataOutputStream dos, int lengthOfBodyContent, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent, String locationUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Location: " + locationUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200MIMEHeader(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
