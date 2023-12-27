package webserver;

import java.io.*;
import java.net.Socket;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            log.debug("request line : {}", line);
            if (line == null) {
                return;
            }

            String[] tokens = line.split(" ");

            int contentLength = 0;
            Map<String, String> cookies = new HashMap<>();
            while (!line.isEmpty()) {
                line = br.readLine();
                log.debug("header : {}", line);
                if(line.contains("Content-Length")) {
                    contentLength = getContentLength(line);
                } else if(line.contains("Cookie")) {
                    cookies = getCookies(line);
                }
            }

            String url = tokens[1];

            if("/user/login".equals(url)) {
                String body = IOUtils.readData(br, contentLength);
                log.debug(contentLength + "");
                Map<String, String> params =
                        HttpRequestUtils.parseQueryString(body);
                User user = DataBase.findUserById(params.get("userId"));
                if((user == null) ||
                        (!user.getPassword().equals(params.get("password")))) {
                    HashMap<String, String> cookie = new HashMap<>();
                    cookie.put("logined", "false");
                    response302Header(new DataOutputStream(out),"/user/login_failed.html", null);
                } else {
                    Map<String, String> cookie = new HashMap<>();
                    cookie.put("logined", "true");
                    response302Header(new DataOutputStream(out), "/index.html", cookie);
                }
            } else if(("/user/list".equals(url))) {
                String isLogined = cookies.get("logined");
                if(isLogined == null) {
                    response302Header(new DataOutputStream(out), "/user/login.html", null);
                }else if(isLogined.equals("true")) {
                    response302Header(new DataOutputStream(out), "/user/list.html", null);
                } else {
                    response302Header(new DataOutputStream(out), "/user/login.html", null);
                }
            }else if(("/user/create".equals(url))) {
                String body = IOUtils.readData(br, contentLength);
                log.debug(contentLength + "");
                Map<String, String> params =
                        HttpRequestUtils.parseQueryString(body);
                User user = new User(params.get("userId"), params.
                        get("password"), params.get("name"), params.get("email"));
                DataBase.addUser(user);
                log.debug("User : {}", user);
                response302Header(new DataOutputStream(out), "/index.html", null);
            } else if (url.startsWith("/user/create")) {
                int index = url.indexOf("?");
                String queryString = url.substring(index+1);
                Map<String, String> params =
                        HttpRequestUtils.parseQueryString(queryString);
                User user = new User(params.get("userId"), params.
                        get("password"), params.get("name"), params.get("email"));
                log.debug("User : {}", user);
                response302Header(new DataOutputStream(out), "/index.html", null);
            } else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File("./webapp" +
                        tokens[1]).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private Map<String, String> getCookies(String line) {
        String[] headerTokens = line.split(":");
        return HttpRequestUtils.parseCookies(headerTokens[1].trim());
    }

    private int getContentLength(String line) {
        String[] headerTokens = line.split(":");
        return Integer.parseInt(headerTokens[1].trim());
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

    private void response302Header(DataOutputStream dos, String url, Map<String, String> cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            if(cookie != null) {
                dos.writeBytes("Set-Cookie: ");
                for (String key : cookie.keySet()) {
                    dos.writeBytes(key + "=" + cookie.get(key) + ";");
                }
                dos.writeBytes("path=/");
                dos.writeBytes("\r\n");
            }
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
