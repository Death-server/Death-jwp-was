package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IOUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static String getFileText(String url) {
        final String BASE_URL = "webapp";
        try {
            File file = new File(BASE_URL + url);
            if(file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                return readData(br, (int) file.length());
            }
        } catch(IOException ioException) {
            // 404 Not Found
            logger.error(ioException.getMessage());
        }
        return null;
    }
}
